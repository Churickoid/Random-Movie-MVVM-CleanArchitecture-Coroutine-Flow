package com.example.randommovie.domain.usecases

import com.example.randommovie.data.request.requestEntity.Item
import com.example.randommovie.domain.MovieInterface
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.SearchOption

class GetRandomMovieUseCase(
    private val movieInterface: MovieInterface,
    private val searchOption: SearchOption
) {

    suspend operator fun invoke(): Movie {
        return movieInterface.getRandomMovie(searchOption)
    }
}