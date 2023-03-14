package com.example.randommovie.data.retrofit

import com.example.randommovie.data.retrofit.request.filter.GenresAndCountriesRequest
import com.example.randommovie.data.retrofit.request.id.MovieIdRequest
import com.example.randommovie.data.retrofit.request.list.MovieListRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiInterface {
    @GET("api/v2.2/films")
    suspend fun getMovieList(
        @Query("page") page: Int = 1,
        @Query("genres") genre: Int?,
        @Query("countries") country: Int?,
        @Query("order") order: String = "RATING",
        @Query("type") type: String = "FILM",
        @Query("ratingFrom") ratingFrom: Int = 1,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Query("yearTo") yearTo: Int = 3000,
    ): MovieListRequest

    @GET("api/v2.2/films/{id}")
    suspend fun getMovieById(@Path("id") id: Long): MovieIdRequest
    @GET("api/v2.2/films/filters")
    suspend fun getGenresAndCounties(): GenresAndCountriesRequest

}