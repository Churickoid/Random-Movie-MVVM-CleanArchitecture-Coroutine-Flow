package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.randommovie.domain.entity.ItemFilter

@Entity(
    tableName = "countries",
    indices = [
        Index("country", unique = true)
    ]
)
data class CountryDb(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val country: String
){
    fun toItemFilter(): ItemFilter {
        return ItemFilter(this.id,this.country)
    }
}