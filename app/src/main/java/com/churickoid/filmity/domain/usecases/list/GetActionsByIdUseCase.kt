package com.churickoid.filmity.domain.usecases.list

import com.churickoid.filmity.domain.ListRepository
import com.churickoid.filmity.domain.entity.Actions

class GetActionsByIdUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(movieId: Long): Actions {
        return listRepository.getActionsByMovieId(movieId)!!
    }
}