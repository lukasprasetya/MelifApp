package com.binar.melif.data.network.api.model

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("results")
    val results: List<TvShowResultModel>
)

data class TvShowResultModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_title")
    val titleName: String,
    @SerializedName("vote_average")
val voteAverage: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String,
)