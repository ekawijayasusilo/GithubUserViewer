package com.example.githubuserviewer.presentation.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserviewer.data.repository.UsersRepositoryImpl
import com.example.githubuserviewer.data.service.UsersService
import com.example.githubuserviewer.databinding.ActivitySearchUserBinding
import com.example.githubuserviewer.domain.usecase.GetUsersUseCase
import com.example.githubuserviewer.presentation.adapters.UserAdapter
import com.example.githubuserviewer.presentation.models.User
import com.example.githubuserviewer.presentation.presenters.SearchUserContract
import com.example.githubuserviewer.presentation.presenters.SearchUserPresenter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class SearchUserActivity : AppCompatActivity(), SearchUserContract.View,
    UserAdapter.UserAdapterListener {

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttp)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val presenter: SearchUserContract.Presenter = SearchUserPresenter(
        this, GetUsersUseCase(
            UsersRepositoryImpl(
                UsersService(retrofit, Gson())
            )
        )
    )
    private val adapter = UserAdapter(this)
    private lateinit var binding: ActivitySearchUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attach()
    }

    override fun onDestroy() {
        presenter.detach()
        adapter.detach()
        super.onDestroy()
    }

    override fun onInitUI() {
        binding.recyclerViewUser.adapter = adapter
        binding.textInputEditTextSearchUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                presenter.loadSearch(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onLoadingSearchResult() {
        binding.recyclerViewUser.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.textInputLayoutSearchUser.isEnabled = false
    }

    override fun onSetSearchResult(users: List<User>) {
        adapter.updateList(users)
        binding.recyclerViewUser.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.textInputLayoutSearchUser.isEnabled = true
    }

    override fun onLoadingNextPageResult() {
        binding.linearLayoutLoadNextPage.visibility = View.VISIBLE
    }

    override fun onSetNextPageResult(users: List<User>) {
        adapter.updateList(users)
        binding.linearLayoutLoadNextPage.visibility = View.GONE
    }

    override fun onSetErrorResult(message: String) {
        binding.recyclerViewUser.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.linearLayoutLoadNextPage.visibility = View.GONE
        binding.textInputLayoutSearchUser.isEnabled = true
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onLoadNextPage() {
        presenter.loadNextPage()
    }
}