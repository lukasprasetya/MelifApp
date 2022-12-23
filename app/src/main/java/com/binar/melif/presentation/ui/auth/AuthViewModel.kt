package com.binar.melif.presentation.ui.auth

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


class AuthViewModel(
    private val userRepository: UserRepository,
    private val prefRepository: LocalRepository
) : ViewModel() {

    val loginResult = MutableLiveData<Resource<Pair<Boolean, User?>>>()
    val prefData =  MutableLiveData<Resource<Unit>>()

    fun authenticateGoogleLogin(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginResult.postValue(Resource.Loading())
            loginResult.postValue(userRepository.signInWithCredential(token))
        }
    }

    fun setSkipIntro(data: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            prefData.postValue(Resource.Loading())
            prefData.postValue(prefRepository.setSkipIntro(data))
        }
    }
}


