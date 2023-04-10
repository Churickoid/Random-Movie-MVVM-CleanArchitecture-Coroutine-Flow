package com.example.randommovie.domain.usecases.filter

import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.entity.ItemFilter

class GetCountriesUseCase(private val filterRepository: FilterRepository
) {
   suspend operator fun invoke(): List<ItemFilter>{
       return filterRepository.getCountriesList()
    }
}