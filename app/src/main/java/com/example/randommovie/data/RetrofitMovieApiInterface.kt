package com.example.randommovie.data

import com.example.randommovie.data.request.MovieListRequest
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitMovieApiInterface {
    @Headers(
        "X-API-KEY:6b31e33d-9d30-4513-9ef6-7705aad38ee1"
    )
    @GET("api/v2.2/films")
    suspend fun getMovieList(
        @Query("page") page: Int = 1,
        //@Query("genres") genre: Int = 1,
        @Query("order") order: String = "NUM_VOTE",
        @Query("type") type: String = "FILM",
        @Query("ratingFrom") ratingFrom: Int = 1,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Query("yearTo") yearTo: Int = 3000,
        ): MovieListRequest

}