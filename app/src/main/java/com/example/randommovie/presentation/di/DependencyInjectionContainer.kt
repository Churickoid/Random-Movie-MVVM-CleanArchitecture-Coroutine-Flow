package com.example.randommovie.presentation.di

import com.example.randommovie.data.FilterRepositoryImpl
import com.example.randommovie.data.MovieRepositoryImpl
import com.example.randommovie.data.RetrofitApiInterface
import com.example.randommovie.domain.usecases.filter.GetCountriesUseCase
import com.example.randommovie.domain.usecases.filter.GetGenresUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.filter.SetSearchFilterUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DependencyInjectionContainer {

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(RetrofitApiInterface::class.java)

    private val movieRepository = MovieRepositoryImpl(retrofit)
    private val filterRepository = FilterRepositoryImpl(retrofit)
    val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository)
    val setSearchFilterUseCase = SetSearchFilterUseCase(filterRepository)
    val getGenresUseCase = GetGenresUseCase(filterRepository)
    val getCountriesUseCase = GetCountriesUseCase(filterRepository)
}