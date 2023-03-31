package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.Actions

class GetActionsByIdUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(movieId: Long): Actions {
        return listRepository.getActionsByMovieId(movieId)!!
    }
}