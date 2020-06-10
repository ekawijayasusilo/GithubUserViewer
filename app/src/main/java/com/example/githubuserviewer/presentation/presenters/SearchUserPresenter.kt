package com.example.githubuserviewer.presentation.presenters

class SearchUserPresenter(private var view: SearchUserContract.View?) :
    SearchUserContract.Presenter {

    override fun attach() {
        view?.onInitUI()
    }

    override fun detach() {
        view = null
    }

    override fun updateSearchTerm(searchTerm: String) {
        TODO("Not yet implemented")
    }

    override fun loadNextPage() {
        TODO("Not yet implemented")
    }
}