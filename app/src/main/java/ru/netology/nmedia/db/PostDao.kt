package ru.netology.nmedia.db

import ru.netology.nmedia.Posts

interface PostDao {
    fun getAll(): List <Posts>
    fun save (post: Posts) :Posts
    fun likedById(id: Int)
    fun removeById(id: Int)
}