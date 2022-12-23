package com.binar.melif.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.melif.data.network.api.model.TvShowResultModel
import com.binar.melif.databinding.ItemTvShowPosterBinding
import com.binar.melif.databinding.ItemTvShowPosterGridBinding


class TvShowListAdapter(
    private val isGridLayout: Boolean = false,
    private val itemClick: (TvShowResultModel)-> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items : MutableList<TvShowResultModel> = mutableListOf()

    fun setItems(items:List<TvShowResultModel>?){
        this.items.clear()
        this.items.addAll(items!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (!isGridLayout) {
            PosterViewHolderImpl(
                ItemTvShowPosterBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false),
                itemClick
            )
        } else {
            GridPosterViewHolderImpl(
                ItemTvShowPosterGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                itemClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PosterViewHolder) {
            holder.bindView(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}