package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "filter",
    primaryKeys = ["movie_id", "item_id"],
    foreignKeys = [
        ForeignKey(
            entity = ItemDb::class,
            parentColumns = ["id", "type"],
            childColumns = ["item_id","item_type"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class FilterDb(
    @PrimaryKey val id: Int,
    @ColumnInfo("year_bottom")val yearBottom: Int,
    @ColumnInfo("year_top")val yearTop: Int,
    @ColumnInfo("rating_bottom")val ratingBottom: Int,
    @ColumnInfo("rating_top")val ratingTop: Int,
    val order: Int,
    val type: Int,
    @ColumnInfo("item_id")val itemId: Int,
    @ColumnInfo("item_type")val itemType: Int
)