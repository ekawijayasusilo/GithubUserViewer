package com.example.githubuserviewer.presentation.presenters

import com.example.githubuserviewer.domain.model.UserEntity
import com.example.githubuserviewer.domain.usecase.GetUsersUseCase
import com.example.githubuserviewer.presentation.models.User

class SearchUserPresenter(
    private var view: SearchUserContract.View?,
    private val getUsers: GetUsersUseCase
) : SearchUserContract.Presenter {

    private var searchTerm = ""
    private var tempSearchTerm = ""
    private var nextPage = 1
    private var users = mutableListOf<User>()

    private val onSuccessSearch: (List<UserEntity>) -> Unit = { users ->
        this.searchTerm = this.tempSearchTerm
        this.nextPage++
        this.users = users.map { User.from(it) }.toMutableList()

        view?.onSetSearchResult(this.users)
    }
    private val onError: (Throwable) -> Unit = { error ->
        view?.onSetErrorResult(error.message ?: "")
    }

    override fun attach() {
        view?.onInitUI()
    }

    override fun detach() {
        getUsers.cancel()
        view = null
    }

    override fun loadSearch(searchTerm: String) {
        this.searchTerm = ""
        this.tempSearchTerm = searchTerm
        this.nextPage = 1

        view?.onLoadingSearchResult()
        getUsers(searchTerm, this.nextPage, this.onSuccessSearch, this.onError)
    }

    override fun loadNextPage() {
        TODO("Not yet implemented")
    }
}