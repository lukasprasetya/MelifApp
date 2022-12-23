package com.binar.melif.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.binar.melif.BuildConfig
import com.binar.melif.databinding.ItemHeaderTvShowBinding
import com.binar.melif.databinding.ItemSectionTvShowBinding
import com.binar.melif.presentation.ui.detail.DetailActivity
import com.binar.melif.presentation.ui.favorite.MovieFavActivity

class MovieAdapter(var listener: ((MovieItem) -> Unit)? = null)
    : RecyclerView.Adapter<ViewHolder>() {

    val data = mutableListOf<MovieItem>()

    fun setItems(newData: List<MovieItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MOVIE_TYPE_HEADER) {
            HomeHeaderMovieItemViewHolder(
                ItemHeaderTvShowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            HomeSectionMovieItemViewHolder(
                ItemSectionTvShowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is HomeHeaderMovieItemViewHolder)
            holder.bind(data[position] as MovieItem.MovieHeaderItem)
        else if (holder is HomeSectionMovieItemViewHolder)
            holder.bind(data[position] as MovieItem.MovieSectionItem)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }
}


class HomeHeaderMovieItemViewHolder(private val binding: ItemHeaderTvShowBinding) :
    ViewHolder(binding.root) {

    fun bind(item: MovieItem.MovieHeaderItem) {
        binding.ivHeaderTvshow.load(BuildConfig.BASE_POSTER_IMG_URL + item.data?.posterPath)
        binding.tvTitleMovie.text = item.data?.name
        binding.ivListLike.setOnClickListener {
            val intent = Intent( itemView.context, MovieFavActivity::class.java)
            itemView.context.startActivity(intent)
        }
        binding.tvRating.text= item.data?.voteAverage.toString()
        binding.tvRilis.text= item.data?.releaseDate.toString()
        binding.tvInfo.setOnClickListener {
            DetailActivity.startActivity(itemView.context, item.data?.id.toString(), "MOVIE")
        }

    }

}

class HomeSectionMovieItemViewHolder(private val binding: ItemSectionTvShowBinding) :
    ViewHolder(binding.root) {

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter {
            DetailActivity.startActivity(itemView.context, it.id.toString(), "MOVIE")
        }
    }

    fun bind(item: MovieItem.MovieSectionItem) {
        binding.tvTitleSection.text = itemView.context.getText(item.sectionName)
        binding.rvContents.adapter = adapter
        adapter.setItemsMovie(item.data)
    }

}