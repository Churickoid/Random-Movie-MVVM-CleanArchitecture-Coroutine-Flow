package com.churickoid.filmity.domain.usecases.list

import com.churickoid.filmity.domain.ListRepository
import kotlinx.coroutines.flow.Flow



class GetMoviesCountByTypeUseCase(private val listRepository: ListRepository) {

    operator fun invoke(type: Int): Flow<Int> {
        return listRepository.getMoviesCountByType(type)
    }
}