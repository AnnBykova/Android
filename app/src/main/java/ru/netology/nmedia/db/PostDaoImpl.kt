package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.Posts

class PostDaoImpl(
    private val db: SQLiteDatabase
) : PostDao {
    override fun getAll(): List<Posts> = db.query(
        PostsTable.NAME,
        PostsTable.ALL_COLUMNS_NAMES,
        null, null, null, null,
        "${PostsTable.Column.ID.columnName} DESC"
    ).use { cursor ->
        List(cursor.count) {
            cursor.moveToNext()
            cursor.toPost()
        }
    }

    override fun removeById(id: Int) {
        db.delete(
            PostsTable.NAME,
            "${PostsTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }

    override fun likedById(id: Int) {
        db.execSQL(
            """
                UPDATE ${PostsTable.NAME} SET
                ${PostsTable.Column.LIKES.columnName} = ${PostsTable.Column.LIKES.columnName} + CASE WHEN ${PostsTable.Column.IS_LIKED.columnName} THEN -1 ELSE 1 END,
                ${PostsTable.Column.IS_LIKED.columnName} = CASE WHEN ${PostsTable.Column.IS_LIKED.columnName} THEN 0 ELSE 1 END
                WHERE id = ?;
            """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun save(post: Posts): Posts {
        val values = ContentValues().apply {
            put(PostsTable.Column.AUTHOR.columnName, post.author)
            put(PostsTable.Column.CONTENT.columnName, post.content)
            put(PostsTable.Column.PUBLISHED.columnName, post.published)
        }
        val id = if (post.id != 0) {
            db.update(
                PostsTable.NAME,
                values,
                "${PostsTable.Column.ID.columnName} = ?",
                arrayOf(post.id.toString())
            )
            post.id
        } else { // post.id=0
            db.insert(PostsTable.NAME, null, values)
        }

        return db.query(
            PostsTable.NAME,
            PostsTable.ALL_COLUMNS_NAMES,
            "${PostsTable.Column.ID.columnName} = ?",
            arrayOf(id.toString()),
            null, null, null, null
        ).use { cursor ->
            cursor.moveToNext()
            cursor.toPost()


        }

    }
}
