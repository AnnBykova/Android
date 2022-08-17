package ru.netology.nmedia.db

import android.database.Cursor
import androidx.core.database.getStringOrNull
import ru.netology.nmedia.Posts

fun PostEntity.toModel() = Posts (
id = id,
author = author,
content = content,
published = published,
likes = likes,
isLiked = isLiked,
share = share,
isShared = isShared,
show = show,
video = video,
)

fun Posts.toEntity() = ru.netology.nmedia.db.PostEntity (
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    isLiked = isLiked,
    share = share,
    isShared = isShared,
    show = show,
    video = video,
)
//fun Cursor.toPost() = Posts (
//    id = getInt(getColumnIndexOrThrow(PostsTable.Column.ID.columnName)),
//    author = getString(getColumnIndexOrThrow(PostsTable.Column.AUTHOR.columnName)),
//    content = getString(getColumnIndexOrThrow(PostsTable.Column.CONTENT.columnName)),
//    published = getString(getColumnIndexOrThrow(PostsTable.Column.PUBLISHED.columnName)),
//    likes = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)),
//    isLiked = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)) !=0,
//    share = getInt(getColumnIndexOrThrow(PostsTable.Column.SHARE.columnName)),
//    isShared = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)) !=0,
//    show = getInt(getColumnIndexOrThrow(PostsTable.Column.SHOW.columnName)),
//    video = getStringOrNull(getColumnIndexOrThrow(PostsTable.Column.VIDEO.columnName)),
//        )