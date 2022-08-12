package ru.netology.nmedia.db

object PostsTable {
    const val NAME = "posts"

    val DDL = """
        CREATE TABLE $NAME (
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.AUTHOR.columnName} TEXT NOT NULL,
	    ${Column.CONTENT.columnName} TEXT NOT NULL,
	    ${Column.PUBLISHED.columnName} TEXT NOT NULL,
	    ${Column.IS_LIKED.columnName} BOOLEAN DEFAULT FALSE,
	    ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 0,
	    ${Column.IS_SHARED.columnName} BOOLEAN DEFAULT FALSE,
	    ${Column.SHARE.columnName} INTEGER NOT NULL DEFAULT 0,
	    ${Column.SHOW.columnName} INTEGER NOT NULL DEFAULT 0,
	    ${Column.VIDEO.columnName} TEXT DEFAULT NULL
        )
    """.trimIndent()

    val ALL_COLUMNS_NAMES = Column.values().map{
        it.columnName
    }.toTypedArray()


    enum class Column(val columnName: String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        IS_LIKED("isLiked"),
        LIKES("likes"),
        SHARE("share"),
        IS_SHARED("isShared"),
        SHOW("show"),
        VIDEO("video")
    }
}