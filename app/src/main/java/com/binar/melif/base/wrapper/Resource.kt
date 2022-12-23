package com.binar.melif.base.wrapper

import java.lang.Exception

sealed class Resource<T>(
    val payload: T? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(exception: Exception?, data: T? = null) : Resource<T>(data, exception = exception)
    class Empty<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)


}
