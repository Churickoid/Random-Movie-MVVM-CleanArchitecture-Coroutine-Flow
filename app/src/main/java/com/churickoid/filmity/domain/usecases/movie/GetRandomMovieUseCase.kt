package com.churickoid.filmity.domain.usecases.movie

import com.churickoid.filmity.domain.MovieRepository
import com.churickoid.filmity.domain.TokenRepository
import com.churickoid.filmity.domain.entity.Movie
import com.churickoid.filmity.domain.entity.SearchFilter

class GetRandomMovieUseCase(
    private val movieRepository: MovieRepository,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(searchFilter: SearchFilter): Movie {
        val tokenKey = tokenRepository.getCurrentToken().apiKey
        return movieRepository.getRandomMovie(searchFilter,tokenKey)
    }
}