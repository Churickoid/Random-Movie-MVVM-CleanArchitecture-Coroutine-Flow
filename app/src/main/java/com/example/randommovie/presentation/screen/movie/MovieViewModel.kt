package com.example.randommovie.presentation.screen.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getRandomMovieUseCase: GetRandomMovieUseCase,
    private val searchFilterUseCase: GetSearchFilterUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean> = _buttonState

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getRandomMovie() {
        viewModelScope.launch {
            _buttonState.value = false
            _error.value = null
            try {
                _movie.value = getRandomMovieUseCase.invoke(searchFilterUseCase())
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _buttonState.value = true
            }
        }
    }
    fun getCurrentMovie():Movie{
        return _movie.value!!
    }

}