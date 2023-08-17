package com.churickoid.filmity.domain.usecases.filter

import com.churickoid.filmity.domain.FilterRepository
import com.churickoid.filmity.domain.TokenRepository
import com.churickoid.filmity.domain.entity.ItemFilter

class GetCountriesUseCase(
    private val filterRepository: FilterRepository,
    private val tokenRepository: TokenRepository
) {
   suspend operator fun invoke(): List<ItemFilter>{
       val tokenKey = tokenRepository.getCurrentToken().apiKey
       return filterRepository.getCountriesList(tokenKey)
    }
}