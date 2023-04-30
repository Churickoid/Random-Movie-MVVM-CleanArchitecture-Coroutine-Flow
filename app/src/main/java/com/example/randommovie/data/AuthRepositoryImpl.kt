package com.example.randommovie.data

import com.example.randommovie.data.retrofit.auth.AuthApi
import com.example.randommovie.data.retrofit.auth.entity.signin.SignInBody
import com.example.randommovie.data.retrofit.auth.entity.signup.SignUpBody
import com.example.randommovie.domain.AuthRepository
import com.example.randommovie.domain.entity.Token

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository {


    override suspend fun signIn(email: String, password: String):Token{
        val response = authApi.authorization(SignInBody(email= email, password = password))
        val headerInfo = response.headers()
        val authToken = headerInfo["authorization"] ?: throw AuthException()
        return authApi.getApiAccount(authToken).toToken()
    }

    override suspend fun signUp(email: String, password: String) {
        val httpCode = authApi.registration(SignUpBody(email,password,password)).code()
        if (httpCode == 400) throw EmailExistException()
    }

    override suspend fun confirmRegistration(code: Int) {
        val httpCode = authApi.confirmRegistration(code).code()
        if (httpCode == 400) throw IncorrectCodeException()
    }


}