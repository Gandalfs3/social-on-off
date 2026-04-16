package com.example.social.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.social.data.local.dao.comment.CommentDao
import com.example.social.data.local.dao.post.PostDao
import com.example.social.data.local.entities.CommentEntity
import com.example.social.data.local.entities.PostEntity

@Database(
    entities = [PostEntity::class, CommentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}