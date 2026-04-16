package com.example.social.domain.repository

import com.example.social.domain.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    suspend fun refreshPosts()
}