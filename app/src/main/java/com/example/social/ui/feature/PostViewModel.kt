package com.example.social.ui.feature.posts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.social.data.local.dao.post.PostDao
import com.example.social.data.local.entities.PostEntity
import com.example.social.data.remote.ApiService
import com.example.social.domain.Post
import com.example.social.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val apiService: ApiService,
    private val postDao: PostDao,
    private val repository: PostRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state: StateFlow<PostState> = _state.asStateFlow()

    /*val posts: StateFlow<List<Post>> = repository.getPosts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )*/

    init {
        //testApiConnection()
        //insertDummyPost()
        //fetchNewPosts()
        observePosts()
        refreshPosts()
    }

    private fun observePosts() {
        repository.getPosts()
            .onEach { posts ->
                _state.update { it.copy(posts = posts) }
            }
            .launchIn(viewModelScope)
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                repository.refreshPosts()
                _state.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Error de conexión: ${e.message}")
                }
            }
        }
    }

    private fun fetchNewPosts() {
        viewModelScope.launch {
            try {
                repository.refreshPosts()
            } catch (e: Exception) {
                Log.e("API_ERROR", "No se pudo actualizar: ${e.message}")
            }
        }
    }

    private fun testApiConnection() {
        viewModelScope.launch {
            try {

                val response = apiService.getPosts()
                Log.d("API_TEST", "Conexión exitosa. Cantidad de posts: ${response.size}")
                if (response.isNotEmpty()) {
                    Log.d("API_TEST", "Primer Post: ${response[0].title}")
                }
            } catch (e: Exception) {
                Log.e("API_TEST", "Error al conectar con la API: ${e.message}")
            }
        }
    }

    private fun insertDummyPost() {
        viewModelScope.launch {
            try {
                val dummy = PostEntity(
                    id = 1,
                    userId = 1,
                    title = "Post de Prueba Local",
                    body = "Si ves esto en el App Inspection, Room está funcionando perfectamente."
                )

                postDao.insertPosts(listOf(dummy))
                Log.d("API_TEST", "Post insertado manualmente en la BD")

            } catch (e: Exception) {
                Log.e("API_TEST", "Error al insertar post manual: ${e.message}")
            }
        }
    }
}