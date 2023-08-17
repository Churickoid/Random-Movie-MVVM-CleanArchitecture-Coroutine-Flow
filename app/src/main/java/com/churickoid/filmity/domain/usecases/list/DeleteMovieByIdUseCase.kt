package com.churickoid.filmity.domain.usecases.list

import com.churickoid.filmity.domain.ListRepository

class DeleteMovieByIdUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(id:Long) {
        listRepository.deleteMovieById(id)
    }
}