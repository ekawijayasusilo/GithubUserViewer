package com.example.githubuserviewer.presentation.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserviewer.databinding.ActivitySearchUserBinding
import com.example.githubuserviewer.presentation.adapters.UserAdapter
import com.example.githubuserviewer.presentation.models.User
import com.example.githubuserviewer.presentation.presenters.SearchUserContract
import com.example.githubuserviewer.presentation.presenters.SearchUserPresenter


class SearchUserActivity : AppCompatActivity(), SearchUserContract.View,
    UserAdapter.UserAdapterListener {

    private val presenter: SearchUserContract.Presenter = SearchUserPresenter(this)
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
        super.onDestroy()
    }

    override fun onInitUI() {
        binding.recyclerViewUser.adapter = adapter
        binding.editTextSearchUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                presenter.updateSearchTerm(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onSetSearchResult(users: List<User>) {
        TODO("Not yet implemented")
    }

    override fun onLoadNextPage(users: List<User>) {
        TODO("Not yet implemented")
    }

    override fun onLoadNextPage() {
        presenter.loadNextPage()
    }
}