package com.binar.melif.presentation.adapter

import androidx.annotation.StringRes
import com.binar.melif.data.network.api.model.MovieResultModel


const val MOVIE_TYPE_HEADER = 0
const val MOVIE_TYPE_SECTION = 1

sealed class MovieItem(val type: Int){
    class MovieHeaderItem(val data: MovieResultModel?):
        MovieItem(MOVIE_TYPE_HEADER)
    class MovieSectionItem(@StringRes val sectionName: Int, val data:
    List<MovieResultModel>?): MovieItem(
        MOVIE_TYPE_SECTION)
}