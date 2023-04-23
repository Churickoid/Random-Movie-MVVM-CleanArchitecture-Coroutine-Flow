package com.example.randommovie.data

import android.util.Log
import com.example.randommovie.domain.TokenRepository
import com.example.randommovie.domain.entity.Token
import com.example.randommovie.domain.entity.Token.Companion.tokens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

class TokenRepositoryImpl: TokenRepository {


    private var token: Token? = null

    override suspend fun deleteToken(token: Token) {
        TODO("Not yet implemented")
    }

    override suspend fun addToken(token: Token) {
        TODO("Not yet implemented")
    }

    override fun getTokenList(): Flow<List<Token>> {
        return flowOf(tokens)
    }

    override fun getCurrentToken(): Token {
        if (token == null) token = tokens[Random.nextInt(tokens.size)]
        return token!!
    }

    override fun setToken(token: Token) {
        this.token = token
    }


}