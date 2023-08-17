package com.churickoid.filmity.presentation.tools

import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.churickoid.filmity.presentation.App
import com.churickoid.filmity.presentation.screen.BaseViewModel
import com.churickoid.filmity.presentation.screen.info.InfoViewModel
import com.churickoid.filmity.presentation.screen.tabs.filter.FilterViewModel
import com.churickoid.filmity.presentation.screen.tabs.list.ListViewModel
import com.churickoid.filmity.presentation.screen.tabs.movie.MovieViewModel
import com.churickoid.filmity.presentation.screen.tabs.settings.SettingsViewModel
import com.churickoid.filmity.presentation.screen.tabs.settings.auth.login.LoginViewModel
import com.churickoid.filmity.presentation.screen.tabs.settings.auth.registration.ConfirmViewModel
import com.churickoid.filmity.presentation.screen.tabs.settings.auth.registration.RegistrationViewModel

@Suppress("UNCHECKED_CAST")
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
                    app.container.getCountriesUseCase,
                    app.container.getSearchFilterUseCase
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
            LoginViewModel::class.java -> {
                LoginViewModel(app.container.signInUseCase)
            }
            RegistrationViewModel::class.java -> {
                RegistrationViewModel(app.container.signUpUseCase)
            }
            ConfirmViewModel::class.java ->{
                ConfirmViewModel(app.container.confirmRegistrationUseCase)
            }
            SettingsViewModel::class.java->{
                SettingsViewModel(app.container.setTokenListUseCase,app.container.deleteTokenUseCase,app.container.getTokenListUseCase)
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
