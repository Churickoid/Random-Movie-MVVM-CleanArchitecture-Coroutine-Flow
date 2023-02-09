package com.example.randommovie.domain.usecases

import com.example.randommovie.domain.MovieInterface
import com.example.randommovie.domain.entity.Movie

class AddMustWatchMovieUseCase(private val movieInterface: MovieInterface, private val movie: Movie){

    operator fun invoke() {
        return movieInterface.addMustWatchedMovie(movie)
    }
}