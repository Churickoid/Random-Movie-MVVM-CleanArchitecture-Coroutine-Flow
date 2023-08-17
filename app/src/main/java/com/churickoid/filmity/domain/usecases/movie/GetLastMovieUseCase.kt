package com.churickoid.filmity.domain.usecases.movie

import com.churickoid.filmity.domain.MovieRepository
import com.churickoid.filmity.domain.entity.Movie

class GetLastMovieUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): Movie? {
        return movieRepository.getLastMovie()
    }

}