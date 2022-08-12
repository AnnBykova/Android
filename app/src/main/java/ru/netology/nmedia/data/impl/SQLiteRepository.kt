package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Posts
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao

class SQLiteRepository (
    private val  dao : PostDao
) : PostRepository {
    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data = MutableLiveData(dao.getAll())

    override fun save(post: Posts) {
        val id = post.id
        val saved = dao.save(post)
        data.value= if (id == 0) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
    }

//    override fun show(post: Posts) {
//        TODO("Not yet implemented")
//    }

    override fun like(id: Int) {
        dao.likedById(id)
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                isLiked = !it.isLiked,
                likes = if (it.isLiked) it.likes - 1 else it.likes + 1
            )
        }
    }

    override fun share(postId: Int) {
        TODO("Not yet implemented")
    }

    override fun delete (id: Int) {
        dao.removeById(id)
        data.value = posts.filter { it.id != id }
    }
}

