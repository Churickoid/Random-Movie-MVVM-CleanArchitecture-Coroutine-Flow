package com.example.randommovie.data.di

import com.example.randommovie.data.FilterRepositoryImpl
import com.example.randommovie.data.MovieRepositoryImpl
import com.example.randommovie.data.RetrofitApiInterface
import com.example.randommovie.domain.usecases.filter.GetCountriesUseCase
import com.example.randommovie.domain.usecases.filter.GetGenresUseCase
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.filter.SetSearchFilterUseCase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers

class DependencyInjectionContainer {

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(
            Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
                .header("X-API-KEY","5c2d749b-5c0c-4809-b62d-a3c98a9f527e")
                //.header("X-API-KEY","6b31e33d-9d30-4513-9ef6-7705aad38ee1")
                .build()
            chain.proceed(requestBuilder)
        })
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(RetrofitApiInterface::class.java)

    private val movieRepository = MovieRepositoryImpl(retrofit)
    val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository)

    private val filterRepository = FilterRepositoryImpl(retrofit)
    val setSearchFilterUseCase = SetSearchFilterUseCase(filterRepository)
    val getSearchFilterUseCase = GetSearchFilterUseCase(filterRepository)
    val getGenresUseCase = GetGenresUseCase(filterRepository)
    val getCountriesUseCase = GetCountriesUseCase(filterRepository)
}