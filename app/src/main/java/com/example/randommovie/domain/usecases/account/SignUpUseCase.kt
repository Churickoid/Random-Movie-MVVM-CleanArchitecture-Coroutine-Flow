package com.example.randommovie.domain.usecases.account
import com.example.randommovie.domain.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.signUp(email,password)
    }
}