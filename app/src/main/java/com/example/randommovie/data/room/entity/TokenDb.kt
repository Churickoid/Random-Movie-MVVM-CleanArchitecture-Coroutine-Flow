package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


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
)