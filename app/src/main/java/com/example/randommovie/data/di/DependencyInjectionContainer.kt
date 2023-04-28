package com.example.randommovie.data.di

import android.content.Context
import androidx.room.Room
import com.example.randommovie.data.*
import com.example.randommovie.data.retrofit.auth.AuthApi
import com.example.randommovie.data.retrofit.movie.MovieApi
import com.example.randommovie.data.room.AppDatabase
import com.example.randommovie.domain.usecases.account.ConfirmRegistrationUseCase
import com.example.randommovie.domain.usecases.account.SignInUseCase
import com.example.randommovie.domain.usecases.account.SignUpUseCase
import com.example.randommovie.domain.usecases.filter.GetCountriesUseCase
import com.example.randommovie.domain.usecases.filter.GetGenresUseCase
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.filter.SetSearchFilterUseCase
import com.example.randommovie.domain.usecases.list.*
import com.example.randommovie.domain.usecases.movie.GetLastMovieUseCase
import com.example.randommovie.domain.usecases.movie.GetMoreInformationUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.movie.SetLastMovieUseCase
import com.example.randommovie.domain.usecases.token.DeleteTokenUseCase
import com.example.randommovie.domain.usecases.token.GetTokenListUseCase
import com.example.randommovie.domain.usecases.token.SetTokenUseCase
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
    private val tokenDao = appDatabase.tokenDao()

    private val movieRepository = MovieRepositoryImpl(movieApi,movieDao,itemsDao)
    private val filterRepository = FilterRepositoryImpl(movieApi,itemsDao,filterDao)
    private val listRepository = ListRepositoryImpl(movieDao,itemsDao,listDao)
    private val authRepository = AuthRepositoryImpl(authApi)
    private val tokenRepository= TokenRepositoryImpl(tokenDao)

    val getRandomMovieUseCase = GetRandomMovieUseCase(movieRepository,tokenRepository)
    val getMoreInformationUseCase = GetMoreInformationUseCase(movieRepository,tokenRepository)
    val getLastMovieUseCase = GetLastMovieUseCase(movieRepository)
    val setLastMovieUseCase = SetLastMovieUseCase(movieRepository)

    val setSearchFilterUseCase = SetSearchFilterUseCase(filterRepository)
    val getSearchFilterUseCase = GetSearchFilterUseCase(filterRepository)
    val getGenresUseCase = GetGenresUseCase(filterRepository,tokenRepository)
    val getCountriesUseCase = GetCountriesUseCase(filterRepository,tokenRepository)

    val getMovieListByFiltersUseCase = GetMovieListByFiltersUseCase(listRepository)
    val getMoviesCountByTypeUseCase = GetMoviesCountByTypeUseCase(listRepository)
    val addUserInfoForMovieUseCase = AddUserInfoForMovieUseCase(listRepository)
    val deleteMovieByIdUseCase= DeleteMovieByIdUseCase(listRepository)
    val getActionsByIdUseCase= GetActionsByIdUseCase(listRepository)

    val getTokenListUseCase = GetTokenListUseCase(tokenRepository)
    val setTokenListUseCase = SetTokenUseCase(tokenRepository)
    val deleteTokenUseCase = DeleteTokenUseCase(tokenRepository)

    val signInUseCase =  SignInUseCase(authRepository,tokenRepository)
    val signUpUseCase = SignUpUseCase(authRepository)
    val confirmRegistrationUseCase = ConfirmRegistrationUseCase(authRepository,signInUseCase)

}