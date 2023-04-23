package com.example.randommovie.domain.usecases.movie

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.TokenRepository
import com.example.randommovie.domain.entity.MovieExtra

class GetMoreInformationUseCase(
    private val movieRepository: MovieRepository,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(id: Long): MovieExtra {
        val tokenKey = tokenRepository.getCurrentToken().apiKey
        return movieRepository.getMoreInformation(id,tokenKey)
    }
}