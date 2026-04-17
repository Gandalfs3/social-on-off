package com.example.social.data.repository

import com.example.social.data.local.dao.post.PostDao
import com.example.social.data.mapper.toDomain
import com.example.social.data.mapper.toEntity
import com.example.social.data.remote.ApiService
import com.example.social.data.remote.dto.PostDto
import com.example.social.domain.Post
import com.example.social.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val postDao: PostDao
) : PostRepository {

    override fun getPosts(): Flow<List<Post>> {
        return postDao.getAllPosts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshPosts() {
        try {
            val remotePosts = apiService.getPosts()
            val entities = remotePosts.map { it.toEntity() }
            postDao.insertPosts(entities)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getPostById(postId: Int): Flow<Post?> {
        return postDao.getPostById(postId).map { it?.toDomain() }
    }
}


