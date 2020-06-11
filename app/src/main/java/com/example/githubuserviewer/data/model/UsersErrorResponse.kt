package com.example.githubuserviewer.data.model

import com.google.gson.annotations.SerializedName

data class UsersErrorResponse(
    @SerializedName("message") val message: String
)