package com.example.social.data.local.dao.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.social.data.local.entities.PostEntity
import com.example.social.data.local.entities.PostWithCommentCount
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Upsert
    suspend fun upsertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("""
    SELECT posts.*, COUNT(comments.id) AS commentCount 
    FROM posts 
    LEFT JOIN comments ON posts.id = comments.postId 
    WHERE posts.title LIKE '%' || :query || '%' OR posts.id = :query
    GROUP BY posts.id
    """
    )
    fun searchPosts(query: String): Flow<List<PostWithCommentCount>>

    @Query("SELECT * FROM posts WHERE id = :postId")
    fun getPostById(postId: Int): Flow<PostEntity?>

    @Transaction
    @Query("""
    SELECT posts.*, COUNT(comments.id) AS commentCount 
    FROM posts 
    LEFT JOIN comments ON posts.id = comments.postId 
    GROUP BY posts.id
    """
    )
    fun getAllPostsWithCommentCount(): Flow<List<PostWithCommentCount>>
}