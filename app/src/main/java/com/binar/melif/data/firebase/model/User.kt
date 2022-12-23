package com.binar.melif.data.firebase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val displayName: String = "",
    val email: String = "",
    val photoProfileUrl: String = ""
) : Parcelable