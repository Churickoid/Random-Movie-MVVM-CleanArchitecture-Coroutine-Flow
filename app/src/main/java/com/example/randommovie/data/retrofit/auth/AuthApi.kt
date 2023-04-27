package com.example.randommovie.data.retrofit.auth

import com.example.randommovie.data.retrofit.auth.entity.signin.AccountInfo
import com.example.randommovie.data.retrofit.auth.entity.signin.SignInBody
import com.example.randommovie.data.retrofit.auth.entity.signup.SignUpBody
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("api/v1/auth/login")
    suspend fun authorization(@Body signInBody: SignInBody): Response<Unit>

    @POST("api/v1/auth/sign-up")
    suspend fun registration(@Body signUpBody: SignUpBody): Response<Unit>

    @GET("api/v1/auth/sign-up/confirm")
    suspend fun confirmRegistration(@Query("code") code: Int): Response<Unit>

    @GET("api/v1/users/me")
    suspend fun getApiAccount(@Header("authorization") auth: String): AccountInfo

}