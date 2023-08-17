package com.churickoid.filmity.domain.usecases.movie

import com.churickoid.filmity.domain.MovieRepository
import com.churickoid.filmity.domain.TokenRepository
import com.churickoid.filmity.domain.entity.MovieExtra

class GetMoreInformationUseCase(
    private val movieRepository: MovieRepository,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(id: Long): MovieExtra {
        val tokenKey = tokenRepository.getCurrentToken().apiKey
        return movieRepository.getMoreInformation(id,tokenKey)
    }
}