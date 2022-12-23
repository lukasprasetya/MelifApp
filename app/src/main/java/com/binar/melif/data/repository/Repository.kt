package com.binar.melif.data.repository

import com.binar.melif.base.BaseRepository
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.local.entity.FavoriteMovieEntity
import com.binar.melif.data.network.api.datasource.FavDataSource
import com.binar.melif.data.network.api.datasource.MelifApiDataSource

import com.binar.melif.data.network.api.model.MelifVideo
import com.binar.melif.data.network.api.model.MovieDetail
import com.binar.melif.data.network.api.model.TvShowDetail
import com.borabor.movieapp.data.local.entity.FavoriteTvEntity

interface Repository {
    suspend fun getDetailTvShow(tv_id: String): Resource<TvShowDetail>
    suspend fun getDetailMovie(movie_id: String): Resource<MovieDetail>
    suspend fun getAllMovieFav(): Resource<List<FavoriteMovieEntity>>
    suspend fun getAllTvFav(): Resource<List<FavoriteTvEntity>>
    suspend fun insertMovieFav(movieFav: FavoriteMovieEntity): Resource<Number>
    suspend fun insertTvFav(movieFav: FavoriteTvEntity): Resource<Number>
    suspend fun getVideoTvShow(tv_id: String): Resource<MelifVideo>
    suspend fun getVideoMovie(movie_id: String): Resource<MelifVideo>
}

class RepositoryImpl(private val networkDataSource: MelifApiDataSource,
                     private val favDataSource: FavDataSource
) : Repository,
    BaseRepository() {
    override suspend fun getDetailTvShow(tv_id: String): Resource<TvShowDetail> {
        return doNetworkCall { networkDataSource.getDetailTvShow(tv_id) }
    }

    override suspend fun getDetailMovie(movie_id: String): Resource<MovieDetail> {
        return doNetworkCall { networkDataSource.getDetailMovie(movie_id) }
    }

    override suspend fun getAllMovieFav(): Resource<List<FavoriteMovieEntity>> {
        return proceed { favDataSource.getAllMovieFav() }
    }

    override suspend fun getAllTvFav(): Resource<List<FavoriteTvEntity>> {
        return proceed { favDataSource.getAllTvFav() }
    }


    override suspend fun insertMovieFav(movieFav: FavoriteMovieEntity): Resource<Number> {
        return proceed { favDataSource.insertMovieFav(movieFav) }
    }

    override suspend fun insertTvFav(tvFav: FavoriteTvEntity): Resource<Number> {
        return proceed { favDataSource.insertTvFav(tvFav) }
    }

    override suspend fun getVideoMovie(movie_id: String): Resource<MelifVideo> {
        return doNetworkCall { networkDataSource.getVideoMovie(movie_id) }
    }

    override suspend fun getVideoTvShow(tv_id: String): Resource<MelifVideo> {
        return doNetworkCall { networkDataSource.getVideoTvShow(tv_id) }
    }
}