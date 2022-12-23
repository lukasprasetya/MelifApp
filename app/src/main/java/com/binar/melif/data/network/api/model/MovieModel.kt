package com.binar.melif.data.network.api.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("results")
    val results: List<MovieResultModel>
)

data class MovieResultModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("original_title")
    val name: String,
    @SerializedName("release_date")
val releaseDate: String,
@SerializedName("vote_average")
val voteAverage: Double,

)
