package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "last_movie"
)
class LastMovieDb(
    @PrimaryKey val id: Long,
    @ColumnInfo("movie_id")val movieId:  Long
)