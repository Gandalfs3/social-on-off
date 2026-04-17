package com.example.social.ui.feature.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.social.domain.Comment
import com.example.social.ui.feature.CommentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    postId: Int,
    onBack: () -> Unit,
    viewModel: CommentViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = postId) {
        viewModel.loadPostData(postId)
    }
    val post by viewModel.post.collectAsState()
    val comments by viewModel.comments.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Post #$postId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                windowInsets = WindowInsets(0.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp, top = 0.dp, bottom = 5.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                post?.let {
                    Column {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it.body,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                    }
                }
            }
            item {
                Text(
                    text = "Comentarios (${comments.size})",
                    style = MaterialTheme.typography.titleMedium
                )
                AddCommentSection(onAdd = { name, body ->
                    viewModel.addComment(postId, name, body)
                })
            }

            items(comments) { comment ->
                CommentItem(comment)
            }
        }
    }
}

@Composable
fun AddCommentSection(onAdd: (String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(modifier = Modifier.padding(12.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tu nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Escribe un comentario...") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if(name.isNotBlank() && body.isNotBlank()) {
                        onAdd(name, body)
                        name = ""
                        body = ""
                    }
                },
                modifier = Modifier.align(androidx.compose.ui.Alignment.End).padding(top = 8.dp)
            ) {
                Text("Publicar")
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = comment.name, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Text(text = comment.body, style = MaterialTheme.typography.bodyMedium)
        }
    }
}