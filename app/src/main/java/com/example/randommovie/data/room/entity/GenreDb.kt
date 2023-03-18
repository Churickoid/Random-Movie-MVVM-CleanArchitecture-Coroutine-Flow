package com.example.randommovie.data.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.randommovie.domain.entity.ItemFilter

@Entity(
    tableName = "genres",
    indices = [
        Index("genre", unique = true)
    ]
)
data class GenreDb(
    @PrimaryKey(autoGenerate = false) val id: Int,
     val genre: String
){

    fun toItemFilter():ItemFilter{
        return ItemFilter(this.id,this.genre)
    }
}