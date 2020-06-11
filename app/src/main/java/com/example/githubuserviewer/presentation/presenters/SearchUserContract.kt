package com.example.githubuserviewer.presentation.presenters

import com.example.githubuserviewer.presentation.models.User

interface SearchUserContract {

    interface View {
        fun onInitUI()

        fun onLoadingSearchResult()

        fun onSetSearchResult(users: List<User>)

        fun onLoadingNextPageResult()

        fun onSetNextPageResult(users: List<User>)

        fun onSetErrorResult(message: String)
    }

    interface Presenter {
        fun attach()

        fun detach()

        fun loadSearch(searchTerm: String)

        fun loadNextPage()
    }

}