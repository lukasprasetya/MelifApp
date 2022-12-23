package com.binar.melif.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.local.entity.FavoriteMovieEntity
import com.binar.melif.data.network.api.model.MelifVideo
import com.binar.melif.data.network.api.model.MovieDetail
import com.binar.melif.data.network.api.model.TvShowDetail
import com.binar.melif.data.repository.Repository
import com.borabor.movieapp.data.local.entity.FavoriteTvEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository, val intentData: Bundle): ViewModel() {

    val detailResultTvShow = MutableLiveData<Resource<TvShowDetail>>()
    val detailResultMovie = MutableLiveData<Resource<MovieDetail>>()
    val insertResult = MutableLiveData<Resource<Number>>()
    private val videoMelif = MutableLiveData<Resource<MelifVideo>>()
    val videos: MutableLiveData<Resource<MelifVideo>> = videoMelif

    fun insertResultMovie(resultData: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertResult.postValue(Resource.Loading())
            delay(1000)

                insertResult.postValue(repository.insertMovieFav(resultData))


        }
    }

    fun insertResultTv(resultData: FavoriteTvEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertResult.postValue(Resource.Loading())
            delay(1000)

                insertResult.postValue(repository.insertTvFav(resultData))


        }
    }

    fun fetchDetail(){
        val Id = intentData.getString(DetailActivity.EXTRAS_ID)
        val dataType = intentData.getString(DetailActivity.EXTRA_TYPE)


            Id.let {
                viewModelScope.launch(Dispatchers.IO) {
                    detailResultTvShow.postValue(Resource.Loading())

                    if (dataType == "TV") {
                        detailResultTvShow.postValue(repository.getDetailTvShow(Id.toString()))
                        fetchVideoTvShow(Id.toString())
                    } else {
                        detailResultMovie.postValue(repository.getDetailMovie(Id.toString()))
                        fetchVideoMovie(Id.toString())
                    }
                }
            }


    }

    fun fetchVideoMovie(movieVideoId: String) {
        movieVideoId.let {
            viewModelScope.launch(Dispatchers.IO) {
                videoMelif.postValue(Resource.Loading())
                videoMelif.postValue(repository.getVideoMovie(movieVideoId))
            }
        }
    }

    fun fetchVideoTvShow(tvShowVideoId: String) {
        tvShowVideoId.let {
            viewModelScope.launch(Dispatchers.IO) {
                videoMelif.postValue(Resource.Loading())
                videoMelif.postValue(repository.getVideoTvShow(tvShowVideoId))

            }
        }
    }

}


