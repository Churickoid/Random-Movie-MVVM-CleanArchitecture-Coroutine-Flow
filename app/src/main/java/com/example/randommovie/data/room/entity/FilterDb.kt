package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey


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