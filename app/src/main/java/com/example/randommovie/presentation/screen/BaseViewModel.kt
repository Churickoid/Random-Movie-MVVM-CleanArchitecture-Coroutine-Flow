package com.example.randommovie.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.usecases.movie.AddRatedMovieUseCase
import kotlinx.coroutines.launch

class BaseViewModel(private val addRatedMovieUseCase: AddRatedMovieUseCase): ViewModel() {

    fun addRatedMovie(movie: Movie){
        viewModelScope.launch {
            addRatedMovieUseCase(movie)
        }
    }
}