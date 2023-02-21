package com.example.randommovie.data.request.filter

import com.example.randommovie.domain.entity.ItemFilter

data class Genre(
    val genre: String,
    val id: Int
){
    fun toItemFilter():ItemFilter{
        return ItemFilter(id,genre)
    }
}