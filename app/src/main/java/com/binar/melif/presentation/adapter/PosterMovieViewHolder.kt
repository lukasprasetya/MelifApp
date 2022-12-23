package com.binar.melif.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.melif.BuildConfig
import com.binar.melif.data.network.api.model.MovieResultModel
import com.binar.melif.databinding.ItemMoviePosterBinding

import com.binar.melif.databinding.ItemTvShowPosterBinding
import com.binar.melif.databinding.ItemTvShowPosterGridBinding

interface PosterMovieViewHolder {
    fun bindView(item: MovieResultModel?)
}

class PosterMovieViewHolderImpl(
    private val binding: ItemMoviePosterBinding,
    val itemClick: (MovieResultModel) -> Unit
) : RecyclerView.ViewHolder(binding.root), PosterMovieViewHolder {

    override fun bindView(item: MovieResultModel?) {
        item?.let { Movie ->
            binding.ivPoster.load(BuildConfig.BASE_POSTER_IMG_URL + Movie.posterPath)
            itemView.setOnClickListener { itemClick(Movie) }
        }
    }


}

class GridPosterMovieViewHolderImpl(
    private val binding: ItemTvShowPosterGridBinding,
    val itemClick: (MovieResultModel) -> Unit
) : RecyclerView.ViewHolder(binding.root), PosterMovieViewHolder {

    override fun bindView(item: MovieResultModel?) {
        item?.let { tvShow ->
            binding.ivPoster.load(BuildConfig.BASE_POSTER_IMG_URL + tvShow.posterPath)
            itemView.setOnClickListener { itemClick(tvShow) }
        }
    }
}
