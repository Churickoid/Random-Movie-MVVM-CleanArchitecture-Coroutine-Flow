package com.example.randommovie.presentation.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.R
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.domain.usecases.list.AddUserInfoForMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BaseViewModel(private val addUserInfoForMovieUseCase: AddUserInfoForMovieUseCase) :
    ViewModel() {

    private val _color = MutableLiveData<Pair<Int,Int>>()
    val color : LiveData<Pair<Int,Int>> = _color
    fun setColor(colorMain: Int, colorBack: Int) {
        _color.value = Pair(colorMain, colorBack)
    }

    fun addRatedMovie(actionsAndMovie: ActionsAndMovie) {
        viewModelScope.launch {
            addUserInfoForMovieUseCase(actionsAndMovie)
        }
    }
}