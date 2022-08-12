package ru.netology.nmedia.db

import android.database.Cursor
import androidx.core.database.getStringOrNull
import ru.netology.nmedia.Posts

fun Cursor.toPost() = Posts (
    id = getInt(getColumnIndexOrThrow(PostsTable.Column.ID.columnName)),
    author = getString(getColumnIndexOrThrow(PostsTable.Column.AUTHOR.columnName)),
    content = getString(getColumnIndexOrThrow(PostsTable.Column.CONTENT.columnName)),
    published = getString(getColumnIndexOrThrow(PostsTable.Column.PUBLISHED.columnName)),
    likes = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)),
    isLiked = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)) !=0,
    share = getInt(getColumnIndexOrThrow(PostsTable.Column.SHARE.columnName)),
    isShared = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)) !=0,
    show = getInt(getColumnIndexOrThrow(PostsTable.Column.SHOW.columnName)),
    video = getStringOrNull(getColumnIndexOrThrow(PostsTable.Column.VIDEO.columnName)),
        )