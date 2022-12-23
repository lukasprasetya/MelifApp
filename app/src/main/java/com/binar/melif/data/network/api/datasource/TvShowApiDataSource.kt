package com.binar.melif.data.network.api.datasource


import com.binar.melif.BuildConfig
import com.binar.melif.data.network.api.model.TvShow
import com.binar.melif.data.network.api.service.MelifApiService


interface TvShowApiDataSource {
    suspend fun getTopRatedTv(page:Int = 1) : TvShow

    suspend fun getPopularTv(page:Int = 1) : TvShow

    suspend fun getAiringTv() :TvShow
}

class TvShowDataSourceImpl(private val service: MelifApiService): TvShowApiDataSource{
    override suspend fun getTopRatedTv(page: Int): TvShow {
        return service.getTopRatedTv(BuildConfig.API_KEY, page)
    }

    override suspend fun getPopularTv(page: Int): TvShow {
        return service.getPopularTv(BuildConfig.API_KEY, page)
    }

    override suspend fun getAiringTv(): TvShow{
        return service.getAiringTv(BuildConfig.API_KEY,1)
    }

}