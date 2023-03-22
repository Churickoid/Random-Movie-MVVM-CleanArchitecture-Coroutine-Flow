package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow

class GetMovieListByTypeUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(type: Int): Flow<List<UserInfoAndMovie>> {
        return listRepository.getMovieListByType(type)
    }
}