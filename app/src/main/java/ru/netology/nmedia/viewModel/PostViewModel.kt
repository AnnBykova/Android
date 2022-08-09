package ru.netology.nmedia.viewModel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Posts
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.SharedPrefsPostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel (
    application: Application
        ): AndroidViewModel(application), PostInteractionListener {
    private val repository: PostRepository =
        FilePostRepository(application)

    val data by repository::data

    // эквивалентная запись
    // val data get() = repository.data

    val sharePostContent= SingleLiveEvent<String>()
    val playVideo= SingleLiveEvent<String>()
    val editPostContent= SingleLiveEvent<String>()
    val showSinglePost= SingleLiveEvent<Int>()
    val deleteSinglePost= SingleLiveEvent<Int>()
    val navigateToPostContentScreenEvent= SingleLiveEvent<String>()
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

//    fun onCancelButtonClicked (){
//        currentPost.value = null
//    }


    // region PostInteractionListener

    override fun onRemoveClicked(post: Posts) {
        repository.delete(post.id)
        deleteSinglePost.value = post.id
    }

    override fun onEditClicked(post: Posts) {
        currentPost.value = post
        editPostContent.value=post.content
    }

    override fun onVideoClicked(post: Posts) {
        val url = requireNotNull(post.video)
        playVideo.value=url
    }

    override fun onPostClicked(post: Posts) {
        currentPost.value = post
        repository.show(post)
        showSinglePost.value = post.id
    }

    override fun onLikeClicked(post: Posts) = repository.like(post.id)

    override fun onShareClicked(post: Posts) {
        sharePostContent.value=post.content
        repository.share(post.id)
    }

//    fun onAddClicked() {
//        navigateToPostContentScreenEvent.call()
//    }

// endregion PostInteractionListener

}