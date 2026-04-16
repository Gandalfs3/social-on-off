package com.example.social.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) : Serializable {
    override fun toString(): String {
        return "PostEntity(id=$id, userId=$userId, title=$title, body=$body )"
    }
}