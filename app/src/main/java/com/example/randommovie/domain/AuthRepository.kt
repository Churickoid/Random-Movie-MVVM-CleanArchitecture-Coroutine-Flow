package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Token

    suspend fun signUp(email: String, password: String): Token


}