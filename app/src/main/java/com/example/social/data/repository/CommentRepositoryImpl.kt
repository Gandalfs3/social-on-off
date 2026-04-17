package com.example.social.data.repository

import com.example.social.data.local.dao.comment.CommentDao
import com.example.social.data.mapper.toDomain
import com.example.social.data.mapper.toEntity
import com.example.social.domain.Comment
import com.example.social.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentDao: CommentDao
) : CommentRepository {

    override fun getCommentsByPost(postId: Int): Flow<List<Comment>> {
        return commentDao.getCommentsForPost(postId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addComment(comment: Comment) {
        commentDao.insertComment(comment.toEntity())
    }
}