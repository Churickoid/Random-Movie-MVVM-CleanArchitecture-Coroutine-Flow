package com.example.randommovie.domain.usecases.movie

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.MovieExtra

class GetMoreInformationUseCase(
    private val movieRepository: MovieRepository,
) {

    suspend operator fun invoke(id: Long): MovieExtra {
        return movieRepository.getMoreInformation(id)
    }
}