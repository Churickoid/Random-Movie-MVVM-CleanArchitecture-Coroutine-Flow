package com.example.randommovie.data

import com.example.randommovie.data.retrofit.auth.AuthApi
import com.example.randommovie.data.retrofit.auth.entity.SignInBody
import com.example.randommovie.domain.AuthRepository
import com.example.randommovie.domain.entity.AccountData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository {


    private val accountData = MutableStateFlow<AccountData?>(null)

    override suspend fun signIn(email: String, password: String){
        val response = authApi.authorization(SignInBody(email= email, password = password))
        val headerInfo = response.headers()
        val authToken = headerInfo["authorization"] ?: throw AuthException()
        accountData.value = authApi.getApiAccount(authToken).toAccount()
    }

    override fun getAccount(): Flow<AccountData?> = accountData

    override suspend fun isSignedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }


}