package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Posts
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    private var posts
        get()= checkNotNull(data.value)
        set(value) {
            data.value=value
        }
    override val data: MutableLiveData<List<Posts>>
    init {
        val initialPosts = listOf(
            Posts(
                id = 1,
                author = "Анна Быкова1",
                content = "Пост номер 1",
                published = "28 июня"

            ),
            Posts(
                id = 2,
                author = "Анна Быкова2",
                content = "После изучения SQLite самое время приступить к изучению списков – List. Но перед этим полезно будет узнать про LayoutInflater. Это знание пригодится нам в создании расширенных списков. Также перед этим уроком рекомендую снова прочесть урок про LayoutParams, освежить знания.",
                published = "29 июня"

            ),
            Posts(
                id = 3,
                author = "Анна Быкова3",
                content = "Пост номер 3",
                published = "30 июня"

            ),
        )
        data = MutableLiveData(initialPosts)
    }

    override fun like(postId:Int) {
        posts=posts.map{post->
            if (post.id==postId) {
                post.copy(isLiked = !post.isLiked,
            likes = if (post.isLiked) post.likes -1 else post.likes +1)
            } else{
                post
            }
        }
    }

    override fun share(postId:Int) {
        posts=posts.map{post->
            if (post.id==postId) {
                post.copy(share = post.share+1)
            } else{
                post
            }
        }
    }

}