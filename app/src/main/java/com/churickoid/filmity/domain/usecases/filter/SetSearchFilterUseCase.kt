package com.churickoid.filmity.domain.usecases.filter

import com.churickoid.filmity.domain.FilterRepository
import com.churickoid.filmity.domain.entity.SearchFilter

class SetSearchFilterUseCase(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(searchFilter: SearchFilter){
        filterRepository.setSearchFilter(searchFilter)
    }
}