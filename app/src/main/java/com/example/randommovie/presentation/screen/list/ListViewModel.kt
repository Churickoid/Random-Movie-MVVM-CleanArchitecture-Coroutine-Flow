package com.example.randommovie.presentation.screen.list

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.usecases.list.GetAllMoviesUseCase
import kotlinx.coroutines.launch

class ListViewModel(private val getAllMoviesUseCase: GetAllMoviesUseCase): ViewModel() {


    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList : LiveData<List<Movie>> = _movieList



     fun getAllMovies(){
        viewModelScope.launch {
            try {
                _movieList.value = getAllMoviesUseCase.invoke()
            }
            catch (e: Exception){
             Log.e("!!!!",e.message ?: "")
            }
        }
    }
}