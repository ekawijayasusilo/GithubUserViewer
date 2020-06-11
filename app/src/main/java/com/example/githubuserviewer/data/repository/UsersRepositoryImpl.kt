package com.example.githubuserviewer.data.repository

import com.example.githubuserviewer.data.service.UsersService
import com.example.githubuserviewer.domain.repository.UsersRepository

class UsersRepositoryImpl(private val service: UsersService) :
    UsersRepository {
    override fun getUsers(searchTerm: String, page: Int) =
        service.getUsers(searchTerm, page).map { it.toUserEntities() }
}