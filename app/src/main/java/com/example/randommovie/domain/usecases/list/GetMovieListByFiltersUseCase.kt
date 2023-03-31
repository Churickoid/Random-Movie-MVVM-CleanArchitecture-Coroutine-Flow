package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.ActionsAndMovie
import kotlinx.coroutines.flow.Flow

class GetMovieListByFiltersUseCase(private val listRepository: ListRepository) {

    operator fun invoke(type: Int, order: Int, isAsc: Boolean): Flow<List<ActionsAndMovie>> {
        return listRepository.getMovieListByFilters(type,order,isAsc)
    }
}