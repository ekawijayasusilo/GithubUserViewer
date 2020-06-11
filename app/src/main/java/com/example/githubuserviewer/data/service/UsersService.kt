package com.example.githubuserviewer.data.service

import com.example.githubuserviewer.data.model.UsersErrorResponse
import com.example.githubuserviewer.data.model.UsersResponse
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Retrofit

class UsersService(retrofit: Retrofit, private val gson: Gson) {
    private val usersAPI = retrofit.create(UsersAPI::class.java)

    fun getUsers(searchTerm: String, page: Int = 1): Single<UsersResponse> {
        return usersAPI.getUsers(searchTerm, page).map { response ->
            if (response.isSuccessful) {
                response.body()
            } else {
                val error = gson.fromJson(
                    response.errorBody()?.string(),
                    UsersErrorResponse::class.java
                )
                throw Exception(error.message)
            }
        }
    }
}