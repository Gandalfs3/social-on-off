package com.example.social.data.remote

import com.example.social.data.remote.dto.PostDto
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<PostDto>
}