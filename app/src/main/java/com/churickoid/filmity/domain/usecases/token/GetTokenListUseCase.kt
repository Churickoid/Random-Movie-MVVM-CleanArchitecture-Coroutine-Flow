package com.churickoid.filmity.domain.usecases.token

import com.churickoid.filmity.domain.TokenRepository
import com.churickoid.filmity.domain.entity.Token
import kotlinx.coroutines.flow.Flow

class GetTokenListUseCase(private val tokenRepository: TokenRepository) {
    operator fun invoke(): Flow<List<Token>> {
        return tokenRepository.getTokenList()
    }
}