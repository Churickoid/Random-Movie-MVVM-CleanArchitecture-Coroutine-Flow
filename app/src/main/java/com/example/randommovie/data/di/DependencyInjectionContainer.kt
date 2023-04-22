package com.example.randommovie.data.di

import android.content.Context
import androidx.room.Room
import com.example.randommovie.data.AuthRepositoryImpl
import com.example.randommovie.data.FilterRepositoryImpl
import com.example.randommovie.data.ListRepositoryImpl
import com.example.randommovie.data.MovieRepositoryImpl
import com.example.randommovie.data.retrofit.auth.AuthApi
import com.example.randommovie.data.retrofit.movie.MovieApi
import com.example.randommovie.data.room.AppDatabase
import com.example.randommovie.domain.usecases.account.SignInUseCase
import com.example.randommovie.domain.usecases.filter.GetCountriesUseCase
import com.example.randommovie.domain.usecases.filter.GetGenresUseCase
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.filter.SetSearchFilterUseCase
import com.example.randommovie.domain.usecases.list.*
import com.example.randommovie.domain.usecases.movie.GetMoreInformationUseCase
import com.example.randommovie.domain.usecases.movie.GetLastMovieUseCase
import com.example.randommovie.domain.usecases.movie.SetLastMovieUseCase
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
                .build()
            chain.proceed(requestBuilder)
        })
        .build()


    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    private val movieApi = retrofitBuilder.create(MovieApi::class.java)
    private val authApi = retrofitBuilder.create(AuthApi::class.java)


    private val appDatabase:AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }

    private val movieDao = appDatabase.moviesDao()
    private val itemsDao = appDatabase.itemsDao()
    private val listDao = appDatabase.listDao()
    private val filterDao = appDatabase.filterDao()

    private val movieRepository = MovieRepositoryImpl(movieApi,movieDao,itemsDao)
    val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository)
    val getMoreInformationUseCase = GetMoreInformationUseCase(movieRepository)
    val getLastMovieUseCase = GetLastMovieUseCase(movieRepository)
    val setLastMovieUseCase = SetLastMovieUseCase(movieRepository)

    private val filterRepository = FilterRepositoryImpl(movieApi,itemsDao,filterDao)
    val setSearchFilterUseCase = SetSearchFilterUseCase(filterRepository)
    val getSearchFilterUseCase = GetSearchFilterUseCase(filterRepository)
    val getGenresUseCase = GetGenresUseCase(filterRepository)
    val getCountriesUseCase = GetCountriesUseCase(filterRepository)

    private val listRepository = ListRepositoryImpl(movieDao,itemsDao,listDao)
    val getMovieListByFiltersUseCase = GetMovieListByFiltersUseCase(listRepository)
    val getMoviesCountByTypeUseCase = GetMoviesCountByTypeUseCase(listRepository)
    val addUserInfoForMovieUseCase = AddUserInfoForMovieUseCase(listRepository)
    val deleteMovieByIdUseCase= DeleteMovieByIdUseCase(listRepository)
    val getActionsByIdUseCase= GetActionsByIdUseCase(listRepository)

    private val authRepository = AuthRepositoryImpl(authApi)
    val signInUseCase =  SignInUseCase(authRepository)

}