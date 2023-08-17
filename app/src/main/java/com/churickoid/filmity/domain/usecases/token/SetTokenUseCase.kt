package com.churickoid.filmity.domain.usecases.token

import com.churickoid.filmity.domain.TokenRepository
import com.churickoid.filmity.domain.entity.Token

class SetTokenUseCase(private val tokenRepository: TokenRepository) {
    operator fun invoke(token:Token){
         tokenRepository.setToken(token)
    }
}
