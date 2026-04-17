package com.example.social.data.local.entities

import androidx.room.Embedded

data class PostWithCommentCount(
    @Embedded val post: PostEntity,
    val commentCount: Int
)
