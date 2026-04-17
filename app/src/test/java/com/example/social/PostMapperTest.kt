package com.example.social.data.mapper

import com.example.social.data.remote.dto.PostDto
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMapperTest {

    @Test
    fun `mapperPostDto`() {
        val dto = PostDto(
            id = 10,
            userId = 1,
            title = "Titulo de prueba",
            body = "Cuerpo de prueba"
        )

        val entity = dto.toEntity()

        assertEquals(10, entity.id)
        assertEquals("Titulo de prueba", entity.title)
        assertEquals("Cuerpo de prueba", entity.body)
        assertEquals(1, entity.userId)
    }
}