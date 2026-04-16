package com.example.social.data.mapper

import com.example.social.data.local.entities.PostEntity
import com.example.social.data.remote.dto.PostDto
import com.example.social.domain.Post

fun PostDto.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

fun PostEntity.toDomain(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}