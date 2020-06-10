package com.example.githubuserviewer.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserviewer.R
import com.example.githubuserviewer.databinding.ItemUserBinding
import com.example.githubuserviewer.presentation.models.User

class UserAdapter(private var listener: UserAdapterListener?) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        if (position == differ.currentList.size - 1) {
            listener?.onLoadNextPage()
        }
    }

    fun updateList(users: List<User>) = differ.submitList(users)

    fun detach() {
        listener = null
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: User) {
            binding.textViewName.text = user.name
            Glide.with(itemView)
                .load(user.profileUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .fallback(R.drawable.ic_baseline_image_24)
                .into(binding.imageViewProfile)
        }
    }

    interface UserAdapterListener {
        fun onLoadNextPage()
    }
}