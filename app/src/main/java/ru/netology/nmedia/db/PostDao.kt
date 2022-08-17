package ru.netology.nmedia.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.Posts

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE posts SET content = :content WHERE id = :id")
    fun updateContentById(id: Int, content: String)

    fun save(post: PostEntity) =
        if (post.id == 0) insert(post) else updateContentById(post.id, post.content)

    @Query("""
        UPDATE posts SET
        likes = likes + CASE WHEN isLiked THEN -1 ELSE 1 END,
        isLiked = CASE WHEN isLiked THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likedById(id: Int)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Int)

    @Query("""
        UPDATE posts SET
        share = share +  1,
        isShared =  1
        WHERE id = :id
        """)
    fun share(id: Int)



//    fun save (post: Posts) :Posts
//    fun likedById(id: Int)
//    fun removeById(id: Int)
}