package com.binar.melif.data.network.api.model

data class MelifVideo(
    val id: Int,
    val results: List<ResultVideo>
)

data class ResultVideo(
    val key: String
)
