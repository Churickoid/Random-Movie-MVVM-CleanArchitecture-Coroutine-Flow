package com.example.randommovie.domain.usecases.filter

import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.TokenRepository
import com.example.randommovie.domain.entity.ItemFilter

class GetCountriesUseCase(
    private val filterRepository: FilterRepository,
    private val tokenRepository: TokenRepository
) {
   suspend operator fun invoke(): List<ItemFilter>{
       val tokenKey = tokenRepository.getCurrentToken().apiKey
       return filterRepository.getCountriesList(tokenKey)
    }
}