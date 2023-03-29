package com.example.randommovie.domain

import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow

interface ListRepository {


    fun getMoviesCountByType(type: Int) : Flow<Int>

    suspend fun getMovieListByFilters(type: Int, order: Int, isAsc: Boolean): Flow<List<UserInfoAndMovie>>

    suspend fun addUserInfoForMovie(userInfoAndMovie: UserInfoAndMovie)

    suspend fun deleteMovieById(id: Long)

    companion object{
        const val WATCH_LIST_TYPE = 0
        const val RATED_LIST_TYPE = 1

        const val QUEUE_ORDER = 0
        const val ALPHABET_ORDER = 10
        const val RATING_ORDER= 20
        const val YEAR_ORDER = 30
        const val USER_RATING_ORDER= 40

        const val GENRE_ITEM_TYPE = 0
        const val COUNTRY_ITEM_TYPE = 1



    }
}