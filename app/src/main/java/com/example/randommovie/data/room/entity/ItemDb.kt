package com.example.randommovie.data.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.randommovie.domain.entity.ItemFilter

@Entity(
    tableName = "items",
    primaryKeys = ["id", "type"],
    indices = [
        Index("name", unique = true)
    ]
)
data class ItemDb(
    val id: Int,
    val type: Int,
    val name: String
){
    fun toItemFilter(): ItemFilter {
        return ItemFilter(this.id,this.name)
    }
}