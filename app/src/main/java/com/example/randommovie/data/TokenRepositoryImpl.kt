package com.example.randommovie.data

import com.example.randommovie.data.room.dao.TokenDao
import com.example.randommovie.data.room.entity.TokenDb
import com.example.randommovie.domain.TokenRepository
import com.example.randommovie.domain.entity.Token
import com.example.randommovie.domain.entity.Token.Companion.defaultTokens
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class TokenRepositoryImpl(private val tokenDao: TokenDao) : TokenRepository {


    private var token: Token? = null

    override suspend fun deleteToken(token: Token) {
        tokenDao.deleteTokenByEmail(token.email)
    }

    override suspend fun addToken(token: Token) {
        tokenDao.insertToken(TokenDb.fromToken(token))
    }

    override fun getTokenList(): Flow<List<Token>> {
        val dbFlow = tokenDao.getTokenList().map { list ->
            list.map {
                it.toToken()
            }
        }
        val defaultFlow = flowOf(defaultTokens)
        return dbFlow.combine(defaultFlow){ dbList,defaultList ->
            dbList+defaultList
        }
    }

    override fun getCurrentToken(): Token {
        if (token == null) token = defaultTokens[Random.nextInt(defaultTokens.size)]
        return token!!
    }

    override fun setToken(token: Token) {
        this.token = token
    }


}