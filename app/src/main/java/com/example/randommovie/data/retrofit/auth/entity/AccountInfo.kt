package com.example.randommovie.data.retrofit.auth.entity

import com.example.randommovie.domain.entity.AccountData

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
    fun toAccount(): AccountData {
    return AccountData(email = this.email, apiKey = token)

    }
}