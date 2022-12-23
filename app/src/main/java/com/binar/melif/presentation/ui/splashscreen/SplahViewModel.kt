package com.binar.melif.presentation.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.firebase.model.User
import com.binar.melif.data.repository.LocalRepository
import com.binar.melif.data.repository.UserRepository
import com.borabor.movieapp.data.local.entity.FavoriteTvEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplahViewModel(
    private val prefRepository: LocalRepository
) : ViewModel() {


    val prefData =  MutableLiveData<Resource<Boolean>>()


    fun isSkipIntro() {
        viewModelScope.launch(Dispatchers.IO) {
            prefData.postValue(Resource.Loading())
            prefData.postValue(prefRepository.isSkipIntro())
        }
    }
}


