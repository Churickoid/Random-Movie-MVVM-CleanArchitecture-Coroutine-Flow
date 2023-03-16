package com.example.randommovie.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randommovie.data.room.entity.GenreDb

@Dao
interface GenresDao {

    @Query("SELECT * FROM genres ORDER BY genre")
    suspend fun getAllGenres() : List<GenreDb>

    @Upsert
    suspend fun addOrReplaceGenre(genre :GenreDb)
}