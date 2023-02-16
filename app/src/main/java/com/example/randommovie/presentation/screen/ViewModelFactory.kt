package com.example.randommovie.presentation.screen

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randommovie.presentation.App
import com.example.randommovie.presentation.screen.filter.FilterViewModel
import com.example.randommovie.presentation.screen.movie.MovieViewModel

class ViewModelFactory(private val app: App) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            MovieViewModel::class.java -> {
                MovieViewModel(app.container.getRandomMovieUseCase)
            }
            FilterViewModel::class.java -> {
                FilterViewModel(app.container.setSearchFilterUseCase)
            }
            else -> {
                throw Exception("Unknown View model class")
            }

        }

        return viewModel as T
    }
}
fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)