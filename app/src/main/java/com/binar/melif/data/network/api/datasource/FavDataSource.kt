package com.binar.melif.data.network.api.datasource

import com.binar.melif.data.local.dao.MovieDao
import com.binar.melif.data.local.dao.TvDao
import com.binar.melif.data.local.entity.FavoriteMovieEntity
import com.borabor.movieapp.data.local.entity.FavoriteTvEntity


interface FavDataSource {
    suspend fun getAllMovieFav(): List<FavoriteMovieEntity>
    suspend fun insertMovieFav(member: FavoriteMovieEntity): Long

    suspend fun getAllTvFav(): List<FavoriteTvEntity>
    suspend fun insertTvFav(member: FavoriteTvEntity): Long


}

class FavDataSourceImpl(private val daoM: MovieDao, private val daoT: TvDao) : FavDataSource {
    override suspend fun getAllMovieFav(): List<FavoriteMovieEntity> {
        return daoM.getAllMovies()
    }


    override suspend fun insertMovieFav(movie: FavoriteMovieEntity): Long {
        return daoM.insertMovie(movie)
    }

    override suspend fun getAllTvFav(): List<FavoriteTvEntity> {
        return daoT.getAllTvs()
    }

    override suspend fun insertTvFav(tv: FavoriteTvEntity): Long {
        return daoT.insertTv(tv)
    }


}