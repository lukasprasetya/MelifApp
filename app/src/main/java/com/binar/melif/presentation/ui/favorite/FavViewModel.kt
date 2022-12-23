package com.binar.melif.presentation.ui.favorite

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.local.entity.FavoriteMovieEntity
import com.binar.melif.data.repository.Repository
import com.binar.melif.presentation.adapter.MovieItem
import com.borabor.movieapp.data.local.entity.FavoriteTvEntity


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class FavViewModel(private val repository: Repository) : ViewModel() {

    val initialDataMovieResult = MutableLiveData<Resource<List<FavoriteMovieEntity>>>()
    val initialDataTvResult = MutableLiveData<Resource<List<FavoriteTvEntity>>>()


    fun getAllMovieResult(){
        viewModelScope.launch(Dispatchers.IO){
            initialDataMovieResult.postValue(Resource.Loading())
            delay(1000)
            initialDataMovieResult.postValue(repository.getAllMovieFav())
        }
    }

    fun getAllTvResult(){
        viewModelScope.launch(Dispatchers.IO){
            initialDataTvResult.postValue(Resource.Loading())
            delay(1000)
            initialDataTvResult.postValue(repository.getAllTvFav())
        }
    }



}


