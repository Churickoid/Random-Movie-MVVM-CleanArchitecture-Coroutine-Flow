package com.churickoid.filmity.data.room.entity

import androidx.room.Entity
import androidx.room.Index
import com.churickoid.filmity.domain.entity.ItemFilter

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
    fun toItemFilter(idList: List<Int>): ItemFilter {
        return ItemFilter(this.id,this.name, this.id in idList)
    }
    fun toItemFilter(value: Boolean): ItemFilter {
        return ItemFilter(this.id,this.name, value)
    }
}