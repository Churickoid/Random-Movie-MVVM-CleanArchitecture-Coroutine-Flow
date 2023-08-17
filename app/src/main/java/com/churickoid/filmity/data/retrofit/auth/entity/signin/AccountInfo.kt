package com.churickoid.filmity.data.retrofit.auth.entity.signin

import com.churickoid.filmity.domain.entity.Token

data class AccountInfo(
    val email: String,
    val emailNotification: Boolean,
    val firstName: Any,
    val id: Int,
    val isActive: Boolean,
    val lastName: Any,
    val quota: Int,
    val token: String,
    val usedQuota: Int
){
    fun toToken(): Token {
    return Token(apiKey = token, email = this.email, true)

    }
}