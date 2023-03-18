package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "user_actions_for_movie",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = androidx.room.ForeignKey.CASCADE,
            onUpdate = androidx.room.ForeignKey.CASCADE
        )],
    indices = [
        Index("movie_id", unique = true)
    ]
)
data class UserActionsForMovieDb(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("movie_id") val movieId: Long,
    val rating: Int,
    @ColumnInfo("in_watchlist") val inWatchlist: Boolean
)