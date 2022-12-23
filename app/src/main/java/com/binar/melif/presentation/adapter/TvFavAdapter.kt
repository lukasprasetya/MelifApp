package com.binar.melif.presentation.adapter;

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.melif.BuildConfig
import com.binar.melif.data.local.entity.FavoriteMovieEntity
import com.binar.melif.databinding.ItemMovieFavBinding
import com.borabor.movieapp.data.local.entity.FavoriteTvEntity


class TvFavAdapter(private val itemClick: (FavoriteTvEntity) -> Unit) :
    RecyclerView.Adapter<TvFavAdapter.HistoryViewHolder>() {

    var playersArrList: ArrayList<String>? = null
    private var items: MutableList<FavoriteTvEntity> = mutableListOf()

    fun setItems(items: List<FavoriteTvEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemMovieFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class HistoryViewHolder(private val binding: ItemMovieFavBinding, val itemClick: (FavoriteTvEntity) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: FavoriteTvEntity) {






            binding.tvIdMember.text = item.name
            binding.tvRating.text = item.voteAverage.toString() + " (" + item.voteCount.toString() + ")"
            binding.tvRilis.text = Html.fromHtml(item.firstAirDate )
            binding.ivPoster.load(BuildConfig.BASE_POSTER_IMG_URL + item.posterPath)
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }

}