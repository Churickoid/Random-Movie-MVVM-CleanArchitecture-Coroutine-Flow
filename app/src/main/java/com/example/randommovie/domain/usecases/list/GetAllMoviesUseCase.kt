package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.UserInfoAndMovie

class GetAllMoviesUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(): List<UserInfoAndMovie>{
        return listRepository.getAllMoviesWithUserActions()
    }
}