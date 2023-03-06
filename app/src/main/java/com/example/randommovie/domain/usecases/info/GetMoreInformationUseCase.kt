package com.example.randommovie.domain.usecases.info

import com.example.randommovie.domain.InfoRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension

class GetMoreInformationUseCase(
    private val infoRepository: InfoRepository,
) {

    suspend operator fun invoke(movie: Movie): MovieExtension {
        return infoRepository.getMoreInformation(movie)
    }
}