package com.example.randommovie.presentation.screen.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.movie.GetLastMovieUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.movie.SetLastMovieUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getRandomMovieUseCase: GetRandomMovieUseCase,
    private val searchFilterUseCase: GetSearchFilterUseCase,
    private val getLastMovieUseCase: GetLastMovieUseCase,
    private val setLastMovieUseCase: SetLastMovieUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean> = _buttonState

    private val _error = MutableLiveData<Event<String?>>()
    val error: LiveData<Event<String?>> = _error

    private val _isFirst = MutableLiveData<Boolean>()
    val isFirst: LiveData<Boolean> = _isFirst

    init {
        viewModelScope.launch {
            val lastMovie = getLastMovieUseCase()
            if (lastMovie == null) _isFirst.value = true
            else {_movie.value = lastMovie
                _isFirst.value = false}
        }
    }

    fun getRandomMovie() {
        viewModelScope.launch {
            _buttonState.value = false
            try {
                val movie = getRandomMovieUseCase(searchFilterUseCase())
                _movie.value = movie
                setLastMovieUseCase(movie)
                if (_isFirst.value!!) _isFirst.value = false
            } catch (e: Exception) {
                _error.value = Event(e.message)
            } finally {
                _buttonState.value = true
            }
        }
    }

    fun getCurrentMovie(): Movie? {
        return _movie.value

    }

}