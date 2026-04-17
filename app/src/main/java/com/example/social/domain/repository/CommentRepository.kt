package com.example.social.domain.repository

import com.example.social.domain.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentsByPost(postId: Int): Flow<List<Comment>>
    suspend fun addComment(comment: Comment)
}