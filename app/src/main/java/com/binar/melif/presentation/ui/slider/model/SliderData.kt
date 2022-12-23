package com.binar.melif.presentation.ui.slider.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SliderData(
    val title: String,
    val desc: String,
    val imgSlider: Int
): Parcelable
