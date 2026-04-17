package com.example.social.domain

data class Comment(
    val id: Int = 0,
    val postId: Int,
    val name: String,
    val body: String
)
