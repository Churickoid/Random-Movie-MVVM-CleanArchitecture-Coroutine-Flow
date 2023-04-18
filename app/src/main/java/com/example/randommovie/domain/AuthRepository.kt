package com.example.randommovie.domain

interface AuthRepository {

   suspend fun isSignedIn(): Boolean

   suspend fun signIn(email: String, password: String): String

   suspend fun logout()
}