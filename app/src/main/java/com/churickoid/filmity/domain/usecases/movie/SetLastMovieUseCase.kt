package com.churickoid.filmity.domain.usecases.movie

import com.churickoid.filmity.domain.MovieRepository
import com.churickoid.filmity.domain.entity.Movie

class SetLastMovieUseCase (private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movie: Movie){
        return movieRepository.setLastMovie(movie)
    }
}