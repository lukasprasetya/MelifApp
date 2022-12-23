package com.binar.melif.data.repository

import com.binar.melif.base.BaseRepository
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.network.api.datasource.MovieApiDataSource
import com.binar.melif.data.network.api.model.MovieModel


interface MovieRepository {
    suspend fun getNowPlayingMovie(page:Int = 1) : Resource<MovieModel>

    suspend fun getPopularMovie(page:Int = 1) : Resource<MovieModel>

    suspend fun getTopRatedMovie(page:Int = 1) :Resource<MovieModel>
}

class MovieRepositoryImpl(private val networkDataSource: MovieApiDataSource): MovieRepository,BaseRepository(){
    override suspend fun getNowPlayingMovie(page: Int): Resource<MovieModel> {
        return doNetworkCall { networkDataSource.getNowPlayingMovie(page) }
    }

    override suspend fun getPopularMovie(page: Int): Resource<MovieModel> {
        return doNetworkCall { networkDataSource.getPopularMovie(page) }
    }

    override suspend fun getTopRatedMovie(page: Int): Resource<MovieModel> {
        return doNetworkCall { networkDataSource.getTopRatedMovie(page) }
    }
}