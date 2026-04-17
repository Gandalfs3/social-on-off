package com.example.social.ui.navigation

import com.example.social.domain.Post

sealed class Screen(val route: String) {
    object PostList : Screen("post_list")
    object PostDetail : Screen("post_detail/{postId}") {
        fun createRoute(postId: Int) = "post_detail/$postId"
    }
}