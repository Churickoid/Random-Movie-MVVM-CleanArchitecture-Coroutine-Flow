package com.churickoid.filmity.domain.usecases.list

import com.churickoid.filmity.domain.ListRepository
import com.churickoid.filmity.domain.entity.ActionsAndMovie

class AddUserInfoForMovieUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(actionsAndMovie: ActionsAndMovie) {
        listRepository.addUserInfoForMovie(actionsAndMovie)
    }
}