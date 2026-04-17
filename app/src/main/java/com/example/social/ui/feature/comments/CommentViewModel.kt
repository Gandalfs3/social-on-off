package com.example.social.ui.feature.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.social.domain.Comment
import com.example.social.domain.Post
import com.example.social.domain.repository.CommentRepository
import com.example.social.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository: CommentRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post.asStateFlow()

    fun loadComments(postId: Int) {
        viewModelScope.launch {
            repository.getCommentsByPost(postId).collect { list ->
                _comments.value = list
            }
        }
    }

    fun addComment(postId: Int, name: String, content: String) {
        viewModelScope.launch {
            val newComment = Comment(
                postId = postId,
                name = name,
                body = content
            )
            repository.addComment(newComment)
        }
    }

    fun loadPostData(postId: Int) {
        viewModelScope.launch {
            postRepository.getPostById(postId).collect { _post.value = it }
        }
        viewModelScope.launch {
            repository.getCommentsByPost(postId).collect { _comments.value = it }
        }
    }
}