package com.binar.melif.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import coil.load
import com.binar.melif.BuildConfig
import com.binar.melif.R
import com.binar.melif.base.BaseViewModelActivity
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.local.entity.FavoriteMovieEntity
import com.binar.melif.data.network.api.model.MovieDetail
import com.binar.melif.data.network.api.model.TvShowDetail
import com.binar.melif.databinding.ActivityDetailBinding
import com.binar.melif.presentation.ui.video.YoutubePlay
import com.borabor.movieapp.data.local.entity.FavoriteTvEntity
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.math.RoundingMode
import java.text.DecimalFormat

class DetailActivity : BaseViewModelActivity<ActivityDetailBinding, DetailViewModel>(
    ActivityDetailBinding::inflate
) {

    val df = DecimalFormat("#,###.##")

    override val viewModel: DetailViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        with(binding) {
            appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                if (collapseLayout.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(
                        collapseLayout
                    )
                ) {
                    toolbar.apply {
                        setBackgroundColor(Color.WHITE)
                        title = intent.getStringExtra("")
                        visibility = View.VISIBLE
                        setNavigationIcon(R.drawable.ic_back)
                    }
                    collapseLayout.setCollapsedTitleTextColor(Color.BLACK)
                } else {
                    toolbar.apply {
                        setBackgroundColor(Color.TRANSPARENT)
                        toolbar.visibility = View.VISIBLE
                        setNavigationIcon(R.drawable.ic_back)
                    }
                    collapseLayout.setExpandedTitleColor(Color.TRANSPARENT)
                }
            })
        }

        binding.fabPlay.setOnClickListener {
            val video = YoutubePlay(viewModel.videos.value!!.payload?.results!!.last().key)
            video.show(supportFragmentManager, "Video")

        }
    }

    override fun initView() {
        super.initView()
       // viewModel.fetchDetailTvShow("1399")
//        viewModel.fetchDetailMovie("505642")

        viewModel.fetchDetail()
    }

    override fun observeData() {
        super.observeData()
        viewModel.detailResultTvShow.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    //do nothing
                }
                is Resource.Error -> {
                    showError()
                    showErrorMessage(it.exception?.message.orEmpty())
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    it.payload?.let { detailTvShow ->
                        showDataTvShow(detailTvShow)
                    }
                }
            }
        }
        viewModel.detailResultMovie.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    //do nothing
                }
                is Resource.Error -> {
                    showError()
                    showErrorMessage(it.exception?.message.orEmpty())
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {

                    it.payload?.let { detailMovie ->
                        showDataMovie(detailMovie)
                    }
                }
            }
        }
        viewModel.detailResultMovie.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    //do nothing
                }
                is Resource.Error -> {
                    showError()
                    showErrorMessage(it.exception?.message.orEmpty())
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    it.payload?.let { detailMovie ->
                        showDataMovie(detailMovie)
                    }
                }
            }
        }
    }

    private fun showError() {
        with(binding) {
            progressBarDetail.isVisible = false
            tvErrorDetail.isVisible = true
            nestedScrollView.isVisible = false
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBarDetail.isVisible = true
            tvErrorDetail.isVisible = false
            nestedScrollView.isVisible = false
        }
    }

    private fun showErrorMessage(msg: String) {
        binding.tvErrorDetail.text = msg
    }

    private fun showDataTvShow(dataTvShow: TvShowDetail) {
        with(binding) {
            progressBarDetail.isVisible = false
            tvErrorDetail.isVisible = false
            nestedScrollView.isVisible = true
            bindDataTvShow(dataTvShow)


        }
    }
    private fun showDataMovie(dataMovie: MovieDetail) {
        with(binding) {
            progressBarDetail.isVisible = false
            tvErrorDetail.isVisible = false
            nestedScrollView.isVisible = true
            bindDataMovie(dataMovie)
        }
    }



    private fun bindDataTvShow(dataTvShow: TvShowDetail) {


        title = dataTvShow.name

        with(binding) {

            val data = dataTvShow?.let { FavoriteTvEntity(id = it.id, posterPath = it.posterPath, firstAirDate=it.firstAirDate, episodeRunTime= 0, name = it.name, voteAverage= it.voteAverage , voteCount= it.voteCount, date=0) }

            binding.ivLike.setOnClickListener {
                if (data != null) {
                    viewModel.insertResultTv(data)
                }
            }

            binding.ivShare.setOnClickListener {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Ayo Tontoh Tv Show " + dataTvShow.name + " dengan Rating " + dataTvShow.voteAverage.toString() )
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
            }


            df.roundingMode = RoundingMode.CEILING
            ivHeaderDetail.load(BuildConfig.BASE_POSTER_IMG_URL + dataTvShow.backdropPath)
            imgPosterDetail.load(BuildConfig.BASE_POSTER_IMG_URL + dataTvShow.posterPath)
            tvTitleDetail.text = dataTvShow.name
            tvRateDetail.text = dataTvShow.voteAverage.toString() + "(" + df.format(dataTvShow.voteCount) + ")"
            tvReleaseDetail.text = dataTvShow.firstAirDate
            tvDescDetail.text = dataTvShow.overview
            collapseLayout.title = dataTvShow.name
            tvGenreDetail.text = ""
            ivTrailer.load(BuildConfig.BASE_POSTER_IMG_URL + dataTvShow.backdropPath)


            dataTvShow.genres.forEachIndexed { index, genre ->
                val result = if (index == dataTvShow.genres.size - 1) {
                    genre.name
                } else {
                    "${genre.name}, "
                }
                tvGenreDetail.append(result)
            }
        }
    }
    private fun bindDataMovie(dataMovie: MovieDetail) {



        title = dataMovie.originalTitle
        with(binding) {
            val data = dataMovie?.let { FavoriteMovieEntity(id = it.id, posterPath = it.posterPath, releaseDate=it.releaseDate, runtime= it.runtime, title= it.originalTitle, voteAverage= it.voteAverage , voteCount= it.voteCount, date=0) }

            binding.ivLike.setOnClickListener {
        if (data != null) {
            viewModel.insertResultMovie(data)
        }
            }

            binding.ivShare.setOnClickListener {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Ayo Tontoh Film " + dataMovie.originalTitle + " dengan Rating " + dataMovie.voteAverage.toString() )
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
            }

            val hours: Int = dataMovie.runtime!! /  60
            val minutes: Int = dataMovie.runtime!! %  60


            ivHeaderDetail.load(BuildConfig.BASE_POSTER_IMG_URL + dataMovie.backdropPath)
            imgPosterDetail.load(BuildConfig.BASE_POSTER_IMG_URL + dataMovie.posterPath)
            tvTitleDetail.text = dataMovie.originalTitle
            tvRateDetail.text = dataMovie.voteAverage.toString() + "(" + df.format(dataMovie.voteCount) + ")"
            tvReleaseDetail.text = Html.fromHtml(dataMovie.releaseDate + " &#x2022; " + hours + "hr  " + minutes + "min")
            tvDescDetail.text = dataMovie.overview
            collapseLayout.title = dataMovie.originalTitle
            tvGenreDetail.text = ""
            ivTrailer.load(BuildConfig.BASE_POSTER_IMG_URL + dataMovie.backdropPath)

            dataMovie.genres.forEachIndexed { index, genre ->
                val result = if (index == dataMovie.genres.size - 1) {
                    genre.name
                } else {
                    "${genre.name}, "
                }
                tvGenreDetail.append(result)
            }
        }
    }

    companion object {
        const val EXTRAS_ID = "EXTRAS_ID"
        const val EXTRA_TYPE = "EXTRA_TYPE"
        fun startActivity(context: Context, Id: String, Type:String) {

            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRAS_ID, Id)
                putExtra(EXTRA_TYPE, Type)
            })
        }
    }


}