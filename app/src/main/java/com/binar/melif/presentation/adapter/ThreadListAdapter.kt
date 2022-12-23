package com.binar.melif.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.binar.melif.R
import com.binar.melif.data.firebase.model.ThreadItem
import com.binar.melif.databinding.ItemThreadBinding
import com.binar.melif.presentation.ui.threaddetail.ThreadDetailActivity

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError


class ThreadListAdapter(
    dataStream: FirebaseRecyclerOptions<ThreadItem>,
    private val onDataExist: () -> Unit,
    private val onLoading: (isLoading: Boolean) -> Unit,
    private val onDataEmpty: () -> Unit,
    private val onDataError: (e: Exception) -> Unit
) : FirebaseRecyclerAdapter<ThreadItem, ThreadListAdapter.ThreadItemViewHolder>(dataStream) {

    init {
        onLoading(true)
    }

    class ThreadItemViewHolder(private val binding: ItemThreadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ThreadItem) {
            binding.ivProfilePict.load(item.creator?.photoProfileUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_person)
                error(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
            binding.tvTitleThread.text = item.title
            binding.tvDateThreadStarter.text = item.date
            binding.tvContentThread.text = item.content
            binding.tvNameThreadStarter.text = itemView.context.getString(
                R.string.text_container_display_creator_thread,
                item.creator?.displayName
            )
            itemView.setOnClickListener {
                ThreadDetailActivity.startActivity(itemView.context, item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadItemViewHolder {
        return ThreadItemViewHolder(
            ItemThreadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ThreadItemViewHolder, position: Int, model: ThreadItem) {
        holder.bind(model)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        onLoading(false)
        if (itemCount < 1) {
            onDataEmpty()
        } else {
            onDataExist()
        }
    }

    override fun onError(error: DatabaseError) {
        super.onError(error)
        onDataError(error.toException())
    }

}