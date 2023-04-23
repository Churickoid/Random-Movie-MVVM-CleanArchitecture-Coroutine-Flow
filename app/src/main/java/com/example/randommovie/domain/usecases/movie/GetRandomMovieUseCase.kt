package com.example.randommovie.domain.usecases.movie

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.TokenRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.SearchFilter

class GetRandomMovieUseCase(
    private val movieRepository: MovieRepository,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(searchFilter: SearchFilter): Movie {
        val tokenKey = tokenRepository.getCurrentToken().apiKey
        return movieRepository.getRandomMovie(searchFilter,tokenKey)
    }
}