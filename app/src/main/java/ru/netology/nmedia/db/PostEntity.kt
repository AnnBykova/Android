package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "posts")
class PostEntity (
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "published")
    val published : String,
    @ColumnInfo(name = "likes")
    var likes: Int = 8_099,
    @ColumnInfo(name = "share")
    var share: Int = 1_549_999,
    @ColumnInfo(name = "show")
    var show : Int = 1_000,
    @ColumnInfo(name = "isLiked")
    var isLiked: Boolean= false,
    @ColumnInfo(name = "isShared")
    var isShared: Boolean= false,
    @ColumnInfo(name = "video")
    val video: String? = null
        )