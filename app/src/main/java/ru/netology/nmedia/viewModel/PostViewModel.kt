package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Posts
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel :ViewModel (){
    private val repository : PostRepository = InMemoryPostRepository ()

    val data by repository::data
    // эквивалентная запись
    // val data get() = repository.data

    fun onLikeClicked (post: Posts) = repository.like(post.id)
    fun onShareClicked (post: Posts) = repository.share(post.id)

}