package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow

class GetAllMoviesUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(): Flow<List<UserInfoAndMovie>> {
        return listRepository.getAllMoviesWithUserActions()
    }
}