package com.example.randommovie.data

import com.example.randommovie.data.request.filter.GenresAndCountriesRequest
import com.example.randommovie.data.request.movie.MovieListRequest
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiInterface {
    @Headers(
        //"X-API-KEY:5c2d749b-5c0c-4809-b62d-a3c98a9f527e"
        "X-API-KEY:6b31e33d-9d30-4513-9ef6-7705aad38ee1"
    )
    @GET("api/v2.2/films")
    suspend fun getMovieList(
        @Query("page") page: Int = 1,
        //@Query("genres") genre: Int = 1,
        @Query("order") order: String = "RATING",
        @Query("type") type: String = "FILM",
        @Query("ratingFrom") ratingFrom: Int = 1,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Query("yearTo") yearTo: Int = 3000,
    ): MovieListRequest

    @Headers(
        //"X-API-KEY:5c2d749b-5c0c-4809-b62d-a3c98a9f527e"
        "X-API-KEY:6b31e33d-9d30-4513-9ef6-7705aad38ee1"
    )
    @GET("api/v2.2/films/filters")
    suspend fun getGenresAndCounties(): GenresAndCountriesRequest

}