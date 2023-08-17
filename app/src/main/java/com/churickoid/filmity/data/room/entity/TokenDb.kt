package com.churickoid.filmity.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.churickoid.filmity.domain.entity.Token


@Entity(
    tableName = "tokens",
    indices = [
        Index("api_key", unique = true),
        Index("email", unique = true)
    ]
)
data class TokenDb (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("api_key")val apiKey: String,
    val email: String,
    @ColumnInfo("is_active")val isActive: Boolean
){
    fun toToken(): Token = Token(
        apiKey = this.apiKey,
        email = this.email,
        isCustom = true
    )

    companion object{
        fun fromToken(token: Token): TokenDb = TokenDb(
            id = 0,
            apiKey = token.apiKey,
            email = token.email,
            isActive = false
        )
    }
}