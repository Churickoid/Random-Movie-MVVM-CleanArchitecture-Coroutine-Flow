package com.example.randommovie.presentation.screen.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.UserInfoAndMovie
import com.example.randommovie.domain.usecases.list.DeleteMovieByIdUseCase
import com.example.randommovie.domain.usecases.list.GetAllMoviesUseCase
import kotlinx.coroutines.launch

class ListViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteMovieByIdUseCase: DeleteMovieByIdUseCase
) : ViewModel() {


    private val _movieList = MutableLiveData<List<UserInfoAndMovie>>()
    val movieList: LiveData<List<UserInfoAndMovie>> = _movieList

    init {
        getAllMovies()
    }

    fun deleteMovieById(id: Long){
        viewModelScope.launch {
            deleteMovieByIdUseCase(id)
        }
    }

    private fun getAllMovies() {
        viewModelScope.launch() {
            getAllMoviesUseCase().collect{
                _movieList.value = it
            }
        }
    }
}