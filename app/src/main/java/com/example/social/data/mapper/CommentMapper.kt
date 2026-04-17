package com.example.social.data.mapper


import com.example.social.data.local.entities.CommentEntity
import com.example.social.domain.Comment

fun CommentEntity.toDomain(): Comment {
    return Comment(
        id = id,
        postId = postId,
        name = name,
        body = body
    )
}

fun Comment.toEntity(): CommentEntity {
    return CommentEntity(
        postId = postId,
        name = name,
        body = body
    )
}