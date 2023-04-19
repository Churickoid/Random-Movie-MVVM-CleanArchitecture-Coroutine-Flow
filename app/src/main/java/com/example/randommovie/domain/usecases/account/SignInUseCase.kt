package com.example.randommovie.domain.usecases.account

import com.example.randommovie.domain.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String){
        authRepository.signIn(email,password)
    }
}