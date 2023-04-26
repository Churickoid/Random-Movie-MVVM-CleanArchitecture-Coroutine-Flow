package com.example.randommovie.domain.usecases.account

import com.example.randommovie.domain.AuthRepository
import com.example.randommovie.domain.TokenRepository

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