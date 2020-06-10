package com.example.githubuserviewer.presentation.presenters

import com.example.githubuserviewer.presentation.models.User

interface SearchUserContract {

    interface View {
        fun onInitUI()

        fun onSetSearchResult(users: List<User>)

        fun onLoadNextPage(users: List<User>)
    }

    interface Presenter {
        fun attach()

        fun detach()

        fun updateSearchTerm(searchTerm: String)

        fun loadNextPage()
    }

}