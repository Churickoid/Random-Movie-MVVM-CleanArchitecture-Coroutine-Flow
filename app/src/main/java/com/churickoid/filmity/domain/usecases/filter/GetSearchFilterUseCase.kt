package com.churickoid.filmity.domain.usecases.filter

import com.churickoid.filmity.domain.FilterRepository
import com.churickoid.filmity.domain.entity.SearchFilter

class GetSearchFilterUseCase(private val filterRepository: FilterRepository) {
    suspend operator fun invoke(): SearchFilter{
        return filterRepository.getSearchFilter()
    }
}