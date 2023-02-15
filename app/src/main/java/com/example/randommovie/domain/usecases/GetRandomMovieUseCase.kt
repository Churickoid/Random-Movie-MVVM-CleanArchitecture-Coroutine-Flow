package com.example.randommovie.domain.usecases

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.SearchFilter

class GetRandomMovieUseCase(
    private val movieRepository: MovieRepository,
    private val searchFilter: SearchFilter
) {

    suspend operator fun invoke(): Movie {
        return movieRepository.getRandomMovie()
    }
}