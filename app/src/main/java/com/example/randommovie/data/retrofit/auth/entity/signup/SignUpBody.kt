package com.example.randommovie.data.retrofit.auth.entity.signup

data class SignUpBody(
    val email: String,
    val password: String,
    val repeatedPassword: String
)