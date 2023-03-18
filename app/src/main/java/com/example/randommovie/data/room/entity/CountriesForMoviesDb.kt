package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "countries_for_movies",
    primaryKeys = ["movie_id", "country_id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CountryDb::class,
            parentColumns = ["id"],
            childColumns = ["country_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]

)
data class CountriesForMoviesDb(
    @ColumnInfo("movie_id", index = true) val movieId: Long,
    @ColumnInfo("country_id") val genreId: Long
)