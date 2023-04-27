package com.example.randommovie.domain.usecases.account

import com.example.randommovie.domain.AuthRepository

class ConfirmRegistrationUseCase(
    private val authRepository: AuthRepository,
    private val signInUseCase: SignInUseCase
) {
    suspend operator fun invoke(email: String, password: String, code:Int) {
        authRepository.confirmRegistration(code)
        signInUseCase(email,password)
    }
}