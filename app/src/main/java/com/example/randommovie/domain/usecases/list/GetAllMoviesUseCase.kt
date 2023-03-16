package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.Movie

class GetAllMoviesUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(): List<Movie>{
        return listRepository.getAllMovies()
    }
}