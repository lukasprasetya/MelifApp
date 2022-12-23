package com.binar.melif.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.melif.BuildConfig
import com.binar.melif.data.network.api.model.MovieResultModel
import com.binar.melif.data.network.api.model.TvShowResultModel
import com.binar.melif.databinding.ItemTvShowPosterBinding
import com.binar.melif.databinding.ItemTvShowPosterGridBinding


interface PosterViewHolder {
    fun bindView(item: TvShowResultModel)
}

class PosterViewHolderImpl(
    private val binding: ItemTvShowPosterBinding,
    val itemClick: (TvShowResultModel) -> Unit
) : RecyclerView.ViewHolder(binding.root), PosterViewHolder {

    override fun bindView(item: TvShowResultModel) {
        item?.let { tvShow ->
            binding.ivPoster.load(BuildConfig.BASE_POSTER_IMG_URL + tvShow.posterPath)
            itemView.setOnClickListener { itemClick(tvShow) }
        }
    }
}

class GridPosterViewHolderImpl(
    private val binding: ItemTvShowPosterGridBinding,
    val itemClick: (TvShowResultModel) -> Unit
) : RecyclerView.ViewHolder(binding.root), PosterViewHolder {

    override fun bindView(item: TvShowResultModel) {
        item?.let { tvShow ->
            binding.ivPoster.load(BuildConfig.BASE_POSTER_IMG_URL + tvShow.posterPath)
            itemView.setOnClickListener { itemClick(tvShow) }
        }
    }
}
