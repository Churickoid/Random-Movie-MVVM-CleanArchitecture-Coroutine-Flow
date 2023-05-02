package com.example.randommovie.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.domain.usecases.list.AddUserInfoForMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BaseViewModel(private val addUserInfoForMovieUseCase: AddUserInfoForMovieUseCase) :
    ViewModel() {

    val color = MutableStateFlow(0xFF2276A0.toInt())
    fun setColor(colorMain: Int) {
        color.value = colorMain
    }

    fun addRatedMovie(actionsAndMovie: ActionsAndMovie) {
        viewModelScope.launch {
            addUserInfoForMovieUseCase(actionsAndMovie)
        }
    }
}