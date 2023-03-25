package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import kotlinx.coroutines.flow.Flow



class GetMoviesCountByTypeUseCase(private val listRepository: ListRepository) {

    operator fun invoke(type: Int): Flow<Int> {
        return listRepository.getMoviesCountByType(type)
    }
}