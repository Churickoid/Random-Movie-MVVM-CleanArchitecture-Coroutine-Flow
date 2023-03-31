package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.ActionsAndMovie

class AddUserInfoForMovieUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(actionsAndMovie: ActionsAndMovie) {
        listRepository.addUserInfoForMovie(actionsAndMovie)
    }
}