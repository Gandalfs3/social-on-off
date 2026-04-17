package com.example.social.ui.feature.posts

import com.example.social.domain.Post

data class PostState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)