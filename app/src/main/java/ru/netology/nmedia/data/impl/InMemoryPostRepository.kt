package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Posts
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }
    private var nextId = GENERATED_POST_AMOUNT + 1
    override val data = MutableLiveData(List(GENERATED_POST_AMOUNT) { index ->
        Posts(
            id = index + 1,
            author = "Анна Быкова",
            content = "Пост номер $index",
            published = "28 июня"

        )
    })


    override fun like(postId: Int) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(
                    isLiked = !post.isLiked,
                    likes = if (post.isLiked) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
    }

    override fun share(postId: Int) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(share = post.share + 1)
            } else {
                post
            }
        }
    }

    override fun delete(postId: Int) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Posts) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    

    private fun update(post: Posts) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Posts) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private companion object {
        const val GENERATED_POST_AMOUNT = 100
    }
}