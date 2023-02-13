package com.example.randommovie.domain.usecases

import com.example.randommovie.domain.MovieInterface
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.SearchFilter

class GetRandomMovieUseCase(
    private val movieInterface: MovieInterface,
    private val searchFilter: SearchFilter
) {

    suspend operator fun invoke(): Movie {
        return movieInterface.getRandomMovie(searchFilter)
    }
}