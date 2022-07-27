package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Posts
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    // эквивалентная запись
    // val data get() = repository.data
    val currentPost = MutableLiveData<Posts?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        ) ?: Posts(
            id = PostRepository.NEW_POST_ID,
            author = "Автор поста",
            content = content,
            published = "сегодня"
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onCancelButtonClicked (){
        currentPost.value = null
    }

    // region PostInteractionListener

    override fun onRemoveClicked(post: Posts) = repository.delete(post.id)
    override fun onEditClicked(post: Posts) {
        currentPost.value = post
    }

    override fun onLikeClicked(post: Posts) = repository.like(post.id)

    override fun onShareClicked(post: Posts) = repository.share(post.id)

// endregion PostInteractionListener

}