package com.example.githubuserviewer.data.service

import com.example.githubuserviewer.data.model.UsersResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UsersAPI {
    companion object {
        const val PATH = "/search/users"
        const val QUERY_PARAM_SEARCH_TERM = "q"
        const val QUERY_PARAM_PAGE = "page"
    }

    @GET(PATH)
    fun getUsers(
        @Query(QUERY_PARAM_SEARCH_TERM) searchTerm: String,
        @Query(QUERY_PARAM_PAGE) page: Int = 1
    ): Single<Response<UsersResponse>>
}