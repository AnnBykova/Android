package ru.netology.nmedia.adapter

import ru.netology.nmedia.Posts

interface PostInteractionListener {
    fun onLikeClicked (post: Posts)
    fun onShareClicked (post: Posts)
    fun onRemoveClicked (post: Posts)
    fun onEditClicked (post: Posts)
    fun onVideoClicked (post: Posts)
    fun onPostClicked (post: Posts)
}