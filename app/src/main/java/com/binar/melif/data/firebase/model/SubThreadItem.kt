package com.binar.melif.data.firebase.model

import androidx.annotation.Keep
import java.util.*


@Keep
data class SubThreadItem(
    var id : String? = "",
    val creator: User? = null,
    val content: String = "",
    val date: String = ""
)

