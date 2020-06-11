package com.example.githubuserviewer.data.model

import com.example.githubuserviewer.domain.model.UserEntity
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String
) {
    fun toUserEntity() = UserEntity(id, name, avatarUrl)
}