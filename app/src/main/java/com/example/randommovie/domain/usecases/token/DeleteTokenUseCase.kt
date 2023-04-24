package com.example.randommovie.domain.usecases.token

import com.example.randommovie.domain.TokenRepository
import com.example.randommovie.domain.entity.Token

class DeleteTokenUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(token: Token){
        tokenRepository.deleteToken(token)
    }
}
