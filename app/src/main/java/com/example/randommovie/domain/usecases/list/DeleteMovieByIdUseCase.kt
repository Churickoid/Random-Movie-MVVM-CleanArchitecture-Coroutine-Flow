package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow

class DeleteMovieByIdUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(id:Long) {
        listRepository.deleteMovieById(id)
    }
}