package com.example.randommovie.domain.usecases.info

import com.example.randommovie.domain.InfoRepository
import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension

class ShowMoreInformationUseCase(
    private val infoRepository: InfoRepository,
) {

    suspend operator fun invoke(movie: Movie): MovieExtension {
        return infoRepository.showMoreInformation(movie)
    }
}