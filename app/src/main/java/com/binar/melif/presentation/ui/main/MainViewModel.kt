package com.binar.melif.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.binar.melif.data.repository.UserRepository


class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun doLogout() {
        userRepository.logoutUser()
    }
}