package com.example.randommovie.domain.usecases.filter

import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter

class GetSearchFilterUseCase(private val filterRepository: FilterRepository) {
    operator fun invoke(): SearchFilter{
        return filterRepository.getSearchFilter()
    }
}