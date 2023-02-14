package com.example.randommovie.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randommovie.presentation.App

class ViewModelFactory(private val app: App) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            MovieViewModel::class.java -> {
                MovieViewModel(app.container.movieRepository)
            }
            else -> {
                throw Exception("Unknown View model class")
            }

        }

        return viewModel as T
    }
}