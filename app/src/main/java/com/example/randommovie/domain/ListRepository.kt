package com.example.randommovie.domain

import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow

interface ListRepository {

    suspend fun getCountriesList(): List<ItemFilter>

    suspend fun getGenresList(): List<ItemFilter>

    suspend fun getAllMoviesWithUserActions(): Flow<List<UserInfoAndMovie>>

    suspend fun addUserInfoForMovie(userInfoAndMovie: UserInfoAndMovie)
}