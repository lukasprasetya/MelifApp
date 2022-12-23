package com.binar.melif.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.melif.data.network.api.model.MovieResultModel
import com.binar.melif.databinding.ItemMoviePosterBinding
import com.binar.melif.databinding.ItemTvShowPosterBinding
import com.binar.melif.databinding.ItemTvShowPosterGridBinding

class MovieListAdapter(
    private val isGridLayout: Boolean = false,
    private val itemClick: (MovieResultModel)-> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items : MutableList<MovieResultModel> = mutableListOf()

    fun setItemsMovie(items:List<MovieResultModel>?){
        this.items.clear()
        this.items.addAll(items!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (!isGridLayout) {
            PosterMovieViewHolderImpl(
                ItemMoviePosterBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false),
                itemClick
            )
        } else {
            GridPosterMovieViewHolderImpl(
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
        if (holder is PosterMovieViewHolder) {
            holder.bindView(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}