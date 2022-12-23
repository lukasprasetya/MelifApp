package com.binar.melif.base


import com.binar.melif.base.exception.ApiErrorException
import com.binar.melif.base.exception.NoInternetConnectionException
import com.binar.melif.base.exception.UnexpectedApiErrorException
import com.binar.melif.base.wrapper.Resource
import retrofit2.HttpException
import java.io.IOException


open class BaseRepository {
    suspend fun <T> doNetworkCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Resource.Error(NoInternetConnectionException())
                is HttpException -> {
                    when (val code = throwable.code()) {
                        in 300..308 -> {
                            Resource.Error(ApiErrorException("Redirect", code))
                        }
                        in 400..451 -> {
                            Resource.Error(ApiErrorException("Client Error", code))
                        }
                        in 500..511 -> {
                            Resource.Error(ApiErrorException("Server Error", code))
                        }
                        else -> {
                            Resource.Error(ApiErrorException(httpCode = code))
                        }
                    }
                }
                else -> {
                    Resource.Error(UnexpectedApiErrorException())
                }
            }
        }
    }

    suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}