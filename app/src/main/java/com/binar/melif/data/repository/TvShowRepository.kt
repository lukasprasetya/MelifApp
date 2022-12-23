package com.binar.melif.data.repository

import com.binar.melif.base.BaseRepository
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.network.api.datasource.TvShowApiDataSource
import com.binar.melif.data.network.api.model.FavoriteTv
import com.binar.melif.data.network.api.model.TvShow


interface TvShowRepository {
    suspend fun getTopRatedTvList(page:Int = 1) : Resource<TvShow>

    suspend fun getPopularTvList(page:Int = 1) : Resource<TvShow>

    suspend fun getAiringTvList() : Resource<TvShow>
}

class TvShowRepositoryImpl(private val networkDataSource: TvShowApiDataSource): TvShowRepository,BaseRepository(){
    override suspend fun getTopRatedTvList(page: Int): Resource<TvShow> {
        return doNetworkCall { networkDataSource.getTopRatedTv(page)  }
    }

    override suspend fun getPopularTvList(page: Int): Resource<TvShow> {
        return doNetworkCall { networkDataSource.getPopularTv(page)  }
    }

    override suspend fun getAiringTvList(): Resource<TvShow> {
        return doNetworkCall { networkDataSource.getAiringTv()  }
    }


}