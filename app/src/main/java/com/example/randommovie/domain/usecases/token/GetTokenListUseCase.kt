package com.example.randommovie.domain.usecases.token

import com.example.randommovie.domain.TokenRepository
import com.example.randommovie.domain.entity.Token
import kotlinx.coroutines.flow.Flow

class GetTokenListUseCase(private val tokenRepository: TokenRepository) {
    operator fun invoke(): Flow<List<Token>> {
        return tokenRepository.getTokenList()
    }
}