package com.example.randommovie.domain.usecases.movie

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie

class SetLastMovieUseCase (private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movie: Movie){
        return movieRepository.setLastMovie(movie)
    }
}