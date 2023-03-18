package com.example.randommovie.domain.usecases.list

import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.UserInfoAndMovie

class AddUserInfoForMovieUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(userInfoAndMovie: UserInfoAndMovie) {
        listRepository.addUserInfoForMovie(userInfoAndMovie)
    }
}