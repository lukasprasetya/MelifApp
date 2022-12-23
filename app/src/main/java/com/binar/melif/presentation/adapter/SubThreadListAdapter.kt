package com.binar.melif.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.binar.melif.R
import com.binar.melif.data.firebase.model.SubThreadItem
import com.binar.melif.data.firebase.model.ThreadItem
import com.binar.melif.data.firebase.model.User
import com.binar.melif.databinding.ItemSubThreadBinding

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError


class SubThreadListAdapter(
    dataStream: FirebaseRecyclerOptions<SubThreadItem>,
    private val data: ThreadItem?,
    private val currentUser: User?,
    private val onDataExist: () -> Unit,
    private val onLoading: (isLoading: Boolean) -> Unit,
    private val onDataEmpty: () -> Unit,
    private val onDataError: (e: Exception) -> Unit
) : FirebaseRecyclerAdapter<SubThreadItem, SubThreadListAdapter.SubThreadItemViewHolder>(dataStream) {

    init {
        onLoading(true)
    }

    class SubThreadItemViewHolder(private val binding: ItemSubThreadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubThreadItem,currentUser: User?, data : ThreadItem?) {


            binding.lyL.visibility = View.GONE
            binding.lyR.visibility = View.GONE

            if ( currentUser?.email == item.creator?.email){
                binding.ivProfilePictR.load(item.creator?.photoProfileUrl){
                    crossfade(true)
                    placeholder(R.drawable.ic_person)
                    error(R.drawable.ic_person)
                    transformations(CircleCropTransformation())
                }
                binding.lyL.visibility = View.GONE
                binding.lyR.visibility = View.VISIBLE
                binding.tvContentThreadR.text = item.content
                binding.tvNameThreadStarterR.text = item.creator?.displayName
                binding.tvDateThreadStarterR.text = item.date
                binding.tvTextOwnerR.isVisible = data?.creator?.displayName == item.creator?.displayName

            }else{
                binding.ivProfilePict.load(item.creator?.photoProfileUrl){
                    crossfade(true)
                    placeholder(R.drawable.ic_person)
                    error(R.drawable.ic_person)
                    transformations(CircleCropTransformation())
                }
                binding.lyL.visibility = View.VISIBLE
                binding.lyR.visibility = View.GONE
                binding.tvContentThreadR.text = item.content
                binding.tvContentThread.text = item.content
                binding.tvDateThreadStarter.text = item.date
                binding.tvNameThreadStarter.text = item.creator?.displayName
                binding.tvTextOwner.isVisible = data?.creator?.displayName == item.creator?.displayName
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubThreadItemViewHolder {
        return SubThreadItemViewHolder(
            ItemSubThreadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubThreadItemViewHolder, position: Int, model: SubThreadItem) {
        holder.bind(model,currentUser, data)
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