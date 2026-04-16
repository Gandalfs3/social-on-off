package com.example.social

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.social.ui.feature.PostListPreviewScreen
import com.example.social.ui.feature.PostListScreen
import com.example.social.ui.feature.posts.PostViewModel
import com.example.social.ui.theme.SocialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("API_TEST", "MainActivity creada, solicitando ViewModel...")
        val test = viewModel.toString()
        setContent {
            SocialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    //PostListPreviewScreen()
                    PostListScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.background) {
        //PostListPreviewScreen()
        PostListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SocialTheme {
        Greeting("Android")
    }
}