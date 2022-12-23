package com.binar.melif.data.network.api.model


import com.google.gson.annotations.SerializedName

data class TvShowGenre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)