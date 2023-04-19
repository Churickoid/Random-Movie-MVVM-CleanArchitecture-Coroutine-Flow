package com.example.randommovie.domain

import com.example.randommovie.domain.entity.AccountData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {


    suspend fun signIn(email: String, password: String)

    fun getAccount(): Flow<AccountData?>

    suspend fun isSignedIn(): Boolean

    suspend fun logout()
}