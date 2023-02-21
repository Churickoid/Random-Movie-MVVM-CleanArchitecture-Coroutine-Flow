package com.example.randommovie.domain.usecases.movie

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie

class AddRatedMovieUseCase(private val movieRepository: MovieRepository, private val movie: Movie) {

    operator fun invoke() {
        return movieRepository.addRatedMovie(movie)
    }
}