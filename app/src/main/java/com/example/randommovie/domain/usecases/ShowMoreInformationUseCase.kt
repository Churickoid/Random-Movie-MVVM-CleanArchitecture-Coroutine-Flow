package com.example.randommovie.domain.usecases

import com.example.randommovie.domain.MovieInterface
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension

class ShowMoreInformationUseCase(
    private val movieInterface: MovieInterface,
    private val movie: Movie
) {

    operator fun invoke(): MovieExtension {
        return movieInterface.showMoreInformation(movie)
    }
}