package com.example.randommovie.domain.usecases

import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter

class GetGenresUseCase( private val filterRepository: FilterRepository) {
    suspend operator fun invoke(): List<ItemFilter>{
        return filterRepository.getGenresList()
    }
}