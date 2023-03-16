package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "genres_for_movies",
    primaryKeys = ["movie_id", "genre_id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreDb::class,
            parentColumns = ["id"],
            childColumns = ["genre_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class GenresForMoviesDb(
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("genre_id") val genreId: Long
)