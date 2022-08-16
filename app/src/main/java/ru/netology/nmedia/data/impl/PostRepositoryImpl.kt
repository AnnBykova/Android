package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.Posts
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl (
    private val  dao : PostDao
) : PostRepository {


    override val data = dao.getAll().map {entities ->
        entities.map {it.toModel()}
    }

    override fun save(post: Posts) {
        if (post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity())
        else dao.updateContentById(post.id, post.content)
    }

//    override fun show(post: Posts) {
//        TODO("Not yet implemented")
//    }

    override fun like(id: Int) {
        dao.likedById(id)
    }

    override fun share(id: Int) {
        dao.share(id)
    }

    override fun delete (id: Int) {
        dao.removeById(id)
    }
}

