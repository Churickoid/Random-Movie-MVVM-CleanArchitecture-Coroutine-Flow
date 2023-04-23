package com.example.randommovie.domain.usecases.token

import com.example.randommovie.domain.TokenRepository
import com.example.randommovie.domain.entity.Token

class SetTokenUseCase(private val tokenRepository: TokenRepository) {
    operator fun invoke(token:Token){
         tokenRepository.setToken(token)
    }
}
