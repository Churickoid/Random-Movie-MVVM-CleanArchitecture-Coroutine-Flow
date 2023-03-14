package com.example.randommovie.data.retrofit.request.filter

import com.example.randommovie.domain.entity.ItemFilter

data class GenreWithId(
    val genre: String,
    val id: Int
){
    fun toItemFilter():ItemFilter{
        return ItemFilter(id,genre)
    }
}