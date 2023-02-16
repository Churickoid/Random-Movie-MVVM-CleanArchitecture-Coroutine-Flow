package com.example.randommovie.presentation.di

import com.example.randommovie.data.MovieRepositoryImpl
import com.example.randommovie.data.RetrofitMovieApiInterface
import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.usecases.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.SetSearchFilterUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DIContainer {

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
        .create(RetrofitMovieApiInterface::class.java)

    val movieRepository = MovieRepositoryImpl(retrofit)
    val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository)
    val setSearchFilterUseCase = SetSearchFilterUseCase(movieRepository)
}