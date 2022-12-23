package com.binar.melif.data.network.api.model

import com.google.gson.annotations.SerializedName

data class VideoList(
    @SerializedName("results")
    val results: List<Video>
)

data class Video(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("type")
    val type: String
)