package com.example.randommovie.presentation.tools

import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randommovie.presentation.App
import com.example.randommovie.presentation.screen.BaseViewModel
import com.example.randommovie.presentation.screen.filter.FilterViewModel
import com.example.randommovie.presentation.screen.info.InfoViewModel
import com.example.randommovie.presentation.screen.list.ListViewModel
import com.example.randommovie.presentation.screen.movie.MovieViewModel

class ViewModelFactory(private val app: App) : AbstractSavedStateViewModelFactory() {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val viewModel = when (modelClass) {
            MovieViewModel::class.java -> {
                MovieViewModel(app.container.getRandomMovieUseCase,
                    app.container.getSearchFilterUseCase,
                    app.container.getLastMovieUseCase,
                    app.container.setLastMovieUseCase,
                    app.container.getActionsByIdUseCase
                )
            }
            FilterViewModel::class.java -> {
                FilterViewModel(app.container.setSearchFilterUseCase,
                    app.container.getGenresUseCase,
                    app.container.getCountriesUseCase
                )
            }
            InfoViewModel::class.java -> {
                InfoViewModel(handle,
                    app.container.getMoreInformationUseCase)
            }
            ListViewModel::class.java -> {
                ListViewModel(app.container.getMovieListByFiltersUseCase,
                    app.container.deleteMovieByIdUseCase,
                    app.container.getMoviesCountByTypeUseCase
                )
            }
            BaseViewModel::class.java -> {
                BaseViewModel(app.container.addUserInfoForMovieUseCase)
            }
            else -> {
                throw Exception("Unknown View model class")
            }

        }

        return viewModel as T
    }
}
fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)
