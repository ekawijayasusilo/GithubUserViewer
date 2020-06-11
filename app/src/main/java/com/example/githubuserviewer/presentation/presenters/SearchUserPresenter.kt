package com.example.githubuserviewer.presentation.presenters

import com.example.githubuserviewer.domain.model.UserEntity
import com.example.githubuserviewer.domain.usecase.GetUsersUseCase
import com.example.githubuserviewer.presentation.models.User
import com.example.githubuserviewer.utils.NotContinuableException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class SearchUserPresenter(
    private var view: SearchUserContract.View?,
    private val getUsers: GetUsersUseCase
) : SearchUserContract.Presenter {

    private var searchTerm = ""
    private var tempSearchTerm = ""
    private var nextPage = 1

    private var users = listOf<User>()
    private var isLoadingNextPage = false
    private var isNextPageAvailable = true

    private val autoSearch = PublishSubject.create<String>()
    private var disposable: Disposable? = null
    private val onLoadSearch: (String) -> Unit = { searchTerm ->
        this.searchTerm = ""
        this.tempSearchTerm = searchTerm
        this.nextPage = 1
        this.isNextPageAvailable = true

        view?.onLoadingSearchResult()
        getUsers.cancel()
        getUsers(searchTerm, this.nextPage, this.onSuccessSearch, this.onError)
    }

    private val onSuccessSearch: (List<UserEntity>) -> Unit = { users ->
        this.searchTerm = this.tempSearchTerm
        this.nextPage++
        this.users = users.map { User.from(it) }.toMutableList()

        view?.onSetSearchResult(this.users)
    }

    private val onSuccessNextPage: (List<UserEntity>) -> Unit = { users ->
        this.nextPage++
        this.users = this.users + users.map { User.from(it) }
        this.isLoadingNextPage = false

        view?.onSetNextPageResult(this.users)
    }

    private val onError: (Throwable) -> Unit = { error ->
        if (error is NotContinuableException) {
            isNextPageAvailable = false
        }
        view?.onSetErrorResult(error.message ?: "")
    }

    override fun attach() {
        view?.onInitUI()
        initAutoSearch()
    }

    override fun detach() {
        getUsers.cancel()
        view = null
    }

    override fun loadSearch(searchTerm: String) = autoSearch.onNext(searchTerm)

    override fun loadNextPage() {
        if (!isLoadingNextPage && isNextPageAvailable) {
            isLoadingNextPage = true

            view?.onLoadingNextPageResult()
            getUsers(searchTerm, nextPage, onSuccessNextPage, onError)
        }
    }

    private fun initAutoSearch() {
        disposable = autoSearch.debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onLoadSearch)
    }
}