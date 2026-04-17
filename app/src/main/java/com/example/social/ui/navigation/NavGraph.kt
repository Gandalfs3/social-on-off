package com.example.social.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.social.ui.feature.posts.PostListScreen
import com.example.social.ui.feature.comments.PostDetailScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PostList.route
    ) {
        composable(route = Screen.PostList.route) {
            PostListScreen(onPostClick = { postId ->
                navController.navigate(Screen.PostDetail.createRoute(postId))
            })
        }

        composable(
            route = Screen.PostDetail.route,
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: 0
            PostDetailScreen(
                postId = postId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}