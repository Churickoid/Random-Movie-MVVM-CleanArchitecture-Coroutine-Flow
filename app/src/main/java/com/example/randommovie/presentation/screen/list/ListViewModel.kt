package com.example.randommovie.presentation.screen.list

import android.util.Log
import androidx.lifecycle.*
import com.example.randommovie.domain.ListRepository.Companion.RATED_TYPE
import com.example.randommovie.domain.ListRepository.Companion.WATCHLIST_TYPE
import com.example.randommovie.domain.entity.UserInfoAndMovie
import com.example.randommovie.domain.usecases.list.DeleteMovieByIdUseCase
import com.example.randommovie.domain.usecases.list.GetMovieListByTypeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ListViewModel(
    private val getMovieListByTypeUseCase: GetMovieListByTypeUseCase,
    private val deleteMovieByIdUseCase: DeleteMovieByIdUseCase
) : ViewModel() {


    val type = MutableStateFlow(WATCHLIST_TYPE)

    //val watchlistCounter =

    val movieList = type.flatMapLatest { getMovieListByTypeUseCase(it) }.asLiveData()


    fun deleteMovieById(id: Long) {
        viewModelScope.launch {
            deleteMovieByIdUseCase(id)
        }
    }
}

