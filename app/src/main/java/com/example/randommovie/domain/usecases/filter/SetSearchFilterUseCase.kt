package com.example.randommovie.domain.usecases.filter

import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.entity.SearchFilter

class SetSearchFilterUseCase(
    private val filterRepository: FilterRepository
) {
    operator fun invoke(searchFilter: SearchFilter){
        filterRepository.setSearchFilter(searchFilter)
    }
}