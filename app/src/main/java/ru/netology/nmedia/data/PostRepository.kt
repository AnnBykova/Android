package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Posts

interface PostRepository {
    val data: LiveData<List<Posts>>

    fun like(postId: Int)
    fun share(postId: Int)
    fun delete(postId: Int)
    fun save (post: Posts)

    companion object{
        const val NEW_POST_ID = 0
    }
}