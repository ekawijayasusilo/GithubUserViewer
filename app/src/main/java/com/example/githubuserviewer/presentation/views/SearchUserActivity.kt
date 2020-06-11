package com.example.githubuserviewer.presentation.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.githubuserviewer.databinding.ActivitySearchUserBinding
import com.example.githubuserviewer.presentation.adapters.UserAdapter
import com.example.githubuserviewer.presentation.models.User
import com.example.githubuserviewer.presentation.presenters.SearchUserContract
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class SearchUserActivity : AppCompatActivity(), SearchUserContract.View,
    UserAdapter.UserAdapterListener {

    private val presenter: SearchUserContract.Presenter by inject { parametersOf(this) }
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
        binding.textInputLayoutSearchUser.editText?.doAfterTextChanged { s ->
            presenter.loadSearch(s.toString())
        }
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