package com.example.randommovie.domain.usecases

import com.example.randommovie.domain.MovieInterface
import com.example.randommovie.domain.entity.Movie

class AddRatedMovieUseCase(private val movieInterface: MovieInterface, private val movie: Movie) {

    operator fun invoke() {
        return movieInterface.addRatedMovie(movie)
    }
}