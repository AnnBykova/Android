package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.Posts
import ru.netology.nmedia.data.PostRepository
import kotlin.properties.Delegates

class FilePostRepository(
    private val application: Application
) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Posts::class.java).type

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )
    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            application.openFileOutput(
                FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }
    private var nextId by Delegates.observable(
        prefs.getInt(NEXT_ID_PREFS_KEY, 0)
    ) { _, _, newValue ->
        prefs.edit { putInt(NEXT_ID_PREFS_KEY, newValue) }
    }

    override val data: MutableLiveData<List<Posts>>

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Posts> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type)}
        } else {
            emptyList()
        }

        data = MutableLiveData(posts)
    }


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
                post.copy(
                    share = post.share + 1,
                    isShared = true
                )
            } else {
                post
            }
        }
    }

    override fun delete(postId: Int) {
        posts = posts.filter { it.id != postId }
    }

//    override fun show(post: Posts) {
//        data.value = posts.map {
//            if (it.id == post.id) post else it
//        }
//    }

    override fun save(post: Posts) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }


    private fun update(post: Posts) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Posts) {
        posts = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private companion object {
        const val GENERATED_POST_AMOUNT = 100
        const val POSTS_PREFS_KEY = "posts"
        const val NEXT_ID_PREFS_KEY = "nextId"
        const val FILE_NAME = "posts.json"
    }
}