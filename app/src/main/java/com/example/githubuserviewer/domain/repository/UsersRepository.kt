package com.example.githubuserviewer.domain.repository

import com.example.githubuserviewer.domain.model.UserEntity
import io.reactivex.Single

interface UsersRepository {
    fun getUsers(searchTerm: String, page: Int): Single<List<UserEntity>>
}