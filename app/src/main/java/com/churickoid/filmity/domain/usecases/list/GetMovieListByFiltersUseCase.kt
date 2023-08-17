package com.churickoid.filmity.domain.usecases.list

import com.churickoid.filmity.domain.ListRepository
import com.churickoid.filmity.domain.entity.ActionsAndMovie
import kotlinx.coroutines.flow.Flow

class GetMovieListByFiltersUseCase(private val listRepository: ListRepository) {

    operator fun invoke(type: Int, order: Int, isAsc: Boolean): Flow<List<ActionsAndMovie>> {
        return listRepository.getMovieListByFilters(type,order,isAsc)
    }
}