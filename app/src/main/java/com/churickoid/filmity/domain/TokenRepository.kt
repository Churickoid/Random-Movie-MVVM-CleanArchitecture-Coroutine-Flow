package com.churickoid.filmity.domain

import com.churickoid.filmity.domain.entity.Token
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    suspend fun deleteToken(token: Token)

    suspend fun addToken(token: Token)

    fun getTokenList(): Flow<List<Token>>

    fun getCurrentToken(): Token

    fun setToken(token:Token)
}