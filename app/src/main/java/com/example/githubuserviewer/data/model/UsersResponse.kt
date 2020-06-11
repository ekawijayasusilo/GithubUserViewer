package com.example.githubuserviewer.data.model

import com.example.githubuserviewer.domain.model.UserEntity
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("total_count") val total: Int,
    @SerializedName("items") val users: List<UserResponse>
) {
    fun toUserEntities(): List<UserEntity> {
        return users.map { it.toUserEntity() }
    }
}