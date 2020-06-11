package com.example.githubuserviewer.domain.usecase

import com.example.githubuserviewer.domain.model.UserEntity
import com.example.githubuserviewer.domain.repository.UsersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GetUsersUseCase(private val usersRepository: UsersRepository) {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    operator fun invoke(
        searchTerm: String, page: Int = 1,
        onSuccess: (List<UserEntity>) -> Unit, onError: (Throwable) -> Unit
    ) {
        val disposable = usersRepository.getUsers(searchTerm, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
    }

    fun cancel() {
        compositeDisposable.clear()
    }
}