package com.binar.melif.data.firebase.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class ThreadItem(
    var id: String = "",
    val creator: User? = null,
    val title: String = "",
    val content: String = "",
    val date: String = ""

) : Parcelable

