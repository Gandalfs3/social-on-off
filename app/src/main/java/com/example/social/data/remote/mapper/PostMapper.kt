package com.example.social.data.remote.mapper

import com.example.social.data.remote.dto.PostDto
import com.example.social.domain.Post

class PostMapper {
    fun PostDto.toDomain(): Post {
        return Post(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }
}