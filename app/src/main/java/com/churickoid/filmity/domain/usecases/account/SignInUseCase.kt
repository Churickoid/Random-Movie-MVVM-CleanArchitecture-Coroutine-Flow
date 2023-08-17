package com.churickoid.filmity.domain.usecases.account

import com.churickoid.filmity.domain.AuthRepository
import com.churickoid.filmity.domain.TokenRepository

class SignInUseCase(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        val token = authRepository.signIn(email, password)
        tokenRepository.setToken(token)
        tokenRepository.addToken(token)
    }
}