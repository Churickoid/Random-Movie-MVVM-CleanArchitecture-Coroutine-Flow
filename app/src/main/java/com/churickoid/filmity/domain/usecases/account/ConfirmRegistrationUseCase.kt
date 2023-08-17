package com.churickoid.filmity.domain.usecases.account

import com.churickoid.filmity.domain.AuthRepository

class ConfirmRegistrationUseCase(
    private val authRepository: AuthRepository,
    private val signInUseCase: SignInUseCase
) {
    suspend operator fun invoke(email: String, password: String, code:Int) {
        authRepository.confirmRegistration(code)
        signInUseCase(email,password)
    }
}