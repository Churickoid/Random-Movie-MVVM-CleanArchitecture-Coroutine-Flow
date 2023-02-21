package com.example.randommovie.domain.usecases.movie

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension

class ShowMoreInformationUseCase(
    private val movieRepository: MovieRepository,
    private val movie: Movie
) {

    operator fun invoke(): MovieExtension {
        return movieRepository.showMoreInformation(movie)
    }
}