package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Token

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Token

    suspend fun signUp(email: String, password: String)

    suspend fun confirmRegistration(code:Int)


}