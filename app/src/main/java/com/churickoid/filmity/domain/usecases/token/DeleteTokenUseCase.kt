package com.churickoid.filmity.domain.usecases.token

import com.churickoid.filmity.domain.TokenRepository
import com.churickoid.filmity.domain.entity.Token

class DeleteTokenUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(token: Token){
        tokenRepository.deleteToken(token)
    }
}
