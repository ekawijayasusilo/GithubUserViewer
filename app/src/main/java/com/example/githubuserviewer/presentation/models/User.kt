package com.example.githubuserviewer.presentation.models

import com.example.githubuserviewer.domain.model.UserEntity

data class User(val id: Long, val name: String, val avatarUrl: String) {
    companion object {
        fun from(user: UserEntity) = User(user.id, user.name, user.avatarUrl)
    }
}