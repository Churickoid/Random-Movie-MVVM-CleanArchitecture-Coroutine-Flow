package com.churickoid.filmity.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "items_for_movies",
    primaryKeys = ["movie_id", "item_id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ItemDb::class,
            parentColumns = ["id", "type"],
            childColumns = ["item_id","type"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ItemsForMoviesDb(
    @ColumnInfo("movie_id",index = true) val movieId: Long,
    @ColumnInfo("item_id") val itemId: Int,
    @ColumnInfo("type") val type: Int,
)