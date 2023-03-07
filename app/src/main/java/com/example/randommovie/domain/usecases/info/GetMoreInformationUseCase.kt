package com.example.randommovie.domain.usecases.info

import com.example.randommovie.domain.InfoRepository
import com.example.randommovie.domain.entity.MovieExtra

class GetMoreInformationUseCase(
    private val infoRepository: InfoRepository,
) {

    suspend operator fun invoke(id: Int): MovieExtra {
        return infoRepository.getMoreInformation(id)
    }
}