package com.example.randommovie.data.retrofit.auth

import com.example.randommovie.data.retrofit.auth.entity.AccountInfo
import com.example.randommovie.data.retrofit.auth.entity.SignInBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("api/v1/auth/login")
    suspend fun authorization(@Body signInBody: SignInBody): Response<Unit>

    @GET("api/v1/users/me")
    suspend fun getApiAccount(@Header("authorization") auth: String): AccountInfo
}