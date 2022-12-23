package com.binar.melif.data.repository

import com.binar.melif.base.BaseRepository
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.firebase.UserAuthDataSource
import com.binar.melif.data.firebase.model.User


interface UserRepository {
    suspend fun signInWithCredential(token: String): Resource<Pair<Boolean, User?>>
    fun getCurrentUser(): User?
    fun logoutUser()
}

class UserRepositoryImpl(
    private val userAuthDataSource: UserAuthDataSource,
) : UserRepository, BaseRepository() {

    override suspend fun signInWithCredential(token: String): Resource<Pair<Boolean, User?>> {
        return proceed { userAuthDataSource.signInWithCredential(token) }
    }

    override fun getCurrentUser(): User? {
        return userAuthDataSource.getCurrentUser()
    }

    override fun logoutUser() {
        return userAuthDataSource.logoutUser()
    }

}