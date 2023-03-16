package com.example.randommovie.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "countries"
)
data class CountryDb(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val country: String
)