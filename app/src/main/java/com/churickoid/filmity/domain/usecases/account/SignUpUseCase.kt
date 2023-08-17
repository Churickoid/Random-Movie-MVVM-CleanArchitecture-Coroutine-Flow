package com.churickoid.filmity.domain.usecases.account
import com.churickoid.filmity.domain.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.signUp(email,password)
    }
}