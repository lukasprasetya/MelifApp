package com.binar.melif.data.repository

import com.binar.melif.base.BaseRepository
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.local.entity.FavoriteMovieEntity
import com.binar.melif.data.network.api.datasource.FavDataSource
import com.binar.melif.data.pref.PreferenceDataSource


interface LocalRepository {
    suspend fun isSkipIntro(): Resource<Boolean>
    suspend fun setSkipIntro(isSkipIntro: Boolean) :  Resource<Unit>
}

class LocalRepositoryImpl(private val prefDataSource: PreferenceDataSource) : BaseRepository(), LocalRepository {
    override suspend fun isSkipIntro(): Resource<Boolean> {
           return proceed {  prefDataSource.isSkipIntro()}
    }

    override suspend fun setSkipIntro(isSkipIntro: Boolean): Resource<Unit> {
        return proceed { prefDataSource.setSkipIntro(isSkipIntro)}
    }


}