package com.binar.melif.presentation.ui.thread

import androidx.lifecycle.ViewModel
import com.binar.melif.data.repository.ChatRepository



class ThreadViewModel(private val repository: ChatRepository) : ViewModel() {
    fun getThreadStreamData() = repository.getThread()
}