package com.example.randommovie.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres"
)
data class GenreDb(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val genre: String
)