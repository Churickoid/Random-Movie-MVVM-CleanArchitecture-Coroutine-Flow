package com.example.randommovie.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.UserInfoAndMovie
import com.example.randommovie.domain.usecases.list.AddUserInfoForMovieUseCase
import kotlinx.coroutines.launch

class BaseViewModel(private val addUserInfoForMovieUseCase: AddUserInfoForMovieUseCase): ViewModel() {

    fun addRatedMovie(userInfoAndMovie: UserInfoAndMovie){
        viewModelScope.launch {
            addUserInfoForMovieUseCase(userInfoAndMovie)
        }
    }

    //TODO добавить перехватчик ошибки и вывод тоста =)
}