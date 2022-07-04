package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Posts

interface PostRepository {
    val data : LiveData <Posts>

    fun like()
    fun share()
}