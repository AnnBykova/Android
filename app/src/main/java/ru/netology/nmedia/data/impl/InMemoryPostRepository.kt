package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Posts
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data= MutableLiveData(
        Posts (
            id = 0,
            author = "Анна Быкова",
            content = "Tекст поста",
            published = "28 июня"

        )
            )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should be not null"
        }
        val likedPost = currentPost.copy(
            isLiked = !currentPost.isLiked,
            likes = if (currentPost.isLiked) currentPost.likes -1 else currentPost.likes +1
        )
        data.value=likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should be not null"
        }
        val sharedPost = currentPost.copy(
            share = currentPost.share+1
        )
        data.value=sharedPost
    }
}