package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository

class DeleteMovieByIdUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(id:Long) {
        listRepository.deleteMovieById(id)
    }
}