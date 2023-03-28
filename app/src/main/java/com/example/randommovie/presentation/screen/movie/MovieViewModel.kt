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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

  init {
        viewModelScope.launch{
                _movie.value = getLastMovieUseCase() ?: Movie(
                    id = 17115,
                    titleMain = "Дилер",
                    titleSecond = "Pusher",
                    posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/17115.jpg",
                    genre = listOf("триллер", "драма", "криминал"),
                    year = 1996,
                    ratingKP = 7.0,
                    ratingIMDB = 7.3,
                    country = listOf("Дания"))
        }
    }

    fun getRandomMovie() {
        viewModelScope.launch {
            _buttonState.value = false
            try {
                val movie = getRandomMovieUseCase(searchFilterUseCase())
                _movie.value = movie
                setLastMovieUseCase(movie)
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