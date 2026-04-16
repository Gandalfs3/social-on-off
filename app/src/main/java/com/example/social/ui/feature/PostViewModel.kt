package com.example.social.ui.feature.posts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.social.data.local.dao.post.PostDao
import com.example.social.data.local.entities.PostEntity
import com.example.social.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val apiService: ApiService,
    private val postDao: PostDao
) : ViewModel() {

    init {
        //testApiConnection()
        insertDummyPost()
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