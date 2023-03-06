package com.example.randommovie.presentation.screen.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension
import com.example.randommovie.domain.usecases.info.GetMoreInformationUseCase
import kotlinx.coroutines.launch

class InfoViewModel(private val getMoreInformationUseCase: GetMoreInformationUseCase): ViewModel() {

    private var _movieInfo = MutableLiveData<MovieExtension>()
    val movieInfo : LiveData<MovieExtension> = _movieInfo

    private var _state = MutableLiveData(LOADING_STATE)
    val state: LiveData<String> = _state


     fun getMovieInfo(movie: Movie){
        viewModelScope.launch {
            _state.value = LOADING_STATE
            try {
                _movieInfo.value = getMoreInformationUseCase.invoke(movie)
                _state.value = VALID_STATE
            }
            catch (e: Exception){
                _state.value = e.message
            }
        }
    }
    companion object{
        const val LOADING_STATE = "LOADING_STATE"
        const val VALID_STATE = "VALID_STATE"
    }
}