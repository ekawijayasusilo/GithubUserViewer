package com.example.githubuserviewer

import com.example.githubuserviewer.data.repository.UsersRepositoryImpl
import com.example.githubuserviewer.data.service.UsersService
import com.example.githubuserviewer.domain.repository.UsersRepository
import com.example.githubuserviewer.domain.usecase.GetUsersUseCase
import com.example.githubuserviewer.presentation.presenters.SearchUserContract
import com.example.githubuserviewer.presentation.presenters.SearchUserPresenter
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<Interceptor> {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    single<CallAdapter.Factory> {
        RxJava2CallAdapterFactory.create()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .build()
    }

    single<Gson> {
        Gson()
    }

    single<UsersService> {
        UsersService(get(), get())
    }

    single<UsersRepository> {
        UsersRepositoryImpl(get())
    }

    single<GetUsersUseCase> {
        GetUsersUseCase(get())
    }

    factory<SearchUserContract.Presenter> { (view: SearchUserContract.View) ->
        SearchUserPresenter(view, get())
    }
}