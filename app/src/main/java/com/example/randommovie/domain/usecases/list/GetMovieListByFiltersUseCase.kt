package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow

class GetMovieListByFiltersUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(type: Int,order: Int, isAsc: Boolean): Flow<List<UserInfoAndMovie>> {
        return listRepository.getMovieListByFilters(type,order,isAsc)
    }
}