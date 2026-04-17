package com.example.social.ui.feature.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
        viewModel.loadComments(postId)
    }

    val comments by viewModel.comments.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Post #$postId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Comentarios (${comments.size})",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            AddCommentSection(onAdd = { name, body ->
                viewModel.addComment(postId, name, body)
            })

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(comments) { comment ->
                    CommentItem(comment)
                }
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