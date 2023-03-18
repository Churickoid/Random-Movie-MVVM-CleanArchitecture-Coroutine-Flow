package com.example.randommovie.domain.usecases

import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter

class GetCountriesUseCase(private val listRepository: ListRepository
) {
   suspend operator fun invoke(): List<ItemFilter>{
       return listRepository.getCountriesList()
    }
}