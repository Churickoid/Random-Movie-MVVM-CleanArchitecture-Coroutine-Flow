package com.example.randommovie.data

import com.example.randommovie.data.retrofit.auth.AuthApi
import com.example.randommovie.data.retrofit.auth.entity.SignInBody
import com.example.randommovie.domain.AuthRepository

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository {
    override suspend fun isSignedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String) :String{
        val response = authApi.authorization(SignInBody(email= email, password = password))
        val headerInfo = response.headers()
        val authToken = headerInfo["authorization"] ?: ""
        val accountInfo = authApi.getApiAccount(authToken)
        return accountInfo.token
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}