package com.example.randommovie.data.di

import android.content.Context
import androidx.room.Room
import com.example.randommovie.data.FilterRepositoryImpl
import com.example.randommovie.data.ListRepositoryImpl
import com.example.randommovie.data.MovieRepositoryImpl
import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.AppDatabase
import com.example.randommovie.domain.usecases.filter.GetCountriesUseCase
import com.example.randommovie.domain.usecases.filter.GetGenresUseCase
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.filter.SetSearchFilterUseCase
import com.example.randommovie.domain.usecases.info.GetMoreInformationUseCase
import com.example.randommovie.domain.usecases.list.GetAllMoviesUseCase
import com.example.randommovie.domain.usecases.movie.AddRatedMovieUseCase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DependencyInjectionContainer(context:Context) {

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

    private val appDatabase:AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }

    private val movieDao = appDatabase.getMoviesDao()
    private val genresDao = appDatabase.getGenresDao()
    private val countriesDao = appDatabase.getCountriesDao()

    private val movieRepository = MovieRepositoryImpl(retrofit, movieDao,countriesDao,genresDao)
    val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository)
    val addRatedMovieUseCase = AddRatedMovieUseCase(movieRepository)

    val getMoreInformationUseCase = GetMoreInformationUseCase(movieRepository)

    private val filterRepository = FilterRepositoryImpl(retrofit,countriesDao,genresDao)
    val setSearchFilterUseCase = SetSearchFilterUseCase(filterRepository)
    val getSearchFilterUseCase = GetSearchFilterUseCase(filterRepository)
    val getGenresUseCase = GetGenresUseCase(filterRepository)
    val getCountriesUseCase = GetCountriesUseCase(filterRepository)

    private val listRepository = ListRepositoryImpl(movieDao,countriesDao,genresDao)
    val getAllMoviesUseCase = GetAllMoviesUseCase(listRepository)

}