package com.example.randommovie.domain.usecases

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.MovieExtension
import com.example.randommovie.domain.entity.SearchFilter

class SetSearchFilterUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(searchFilter: SearchFilter){
        movieRepository.setSearchFilter(searchFilter)
    }
}