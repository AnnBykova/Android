package ru.netology.nmedia

data class Posts (
    val id : Int,
    val author: String,
    val content: String,
    val published : String,
    var likes: Int = 8_099,
    var share: Int = 1_549_999,
    var show : Int = 1_000,
    var isLiked: Boolean= false,
    var isShared: Boolean= false,
    val video: String? = null
        )

