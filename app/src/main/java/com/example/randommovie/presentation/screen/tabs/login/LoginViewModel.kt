package com.example.randommovie.presentation.screen.tabs.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.data.AuthException
import com.example.randommovie.domain.usecases.account.SignInUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class LoginViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _emailState = MutableLiveData<Event<Int>>()
    val emailState: LiveData<Event<Int>> = _emailState

    private val _passState = MutableLiveData<Event<Int>>()
    val passState: LiveData<Event<Int>> = _passState

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int> = _state

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast

    private val _startTabsFragment = MutableLiveData<Event<Unit>>()
    val startTabsFragment = _startTabsFragment


    fun signIn(email: String, password: String) {
        if (email.isEmpty()) _emailState.value = Event(EMPTY_ERROR)
        if (password.isEmpty()) _passState.value = Event(EMPTY_ERROR)
        if (email.isEmpty() || password.isEmpty()) return
        viewModelScope.launch {
            try {
                _state.value = LOADING_STATE
                signInUseCase(email, password)
                _startTabsFragment.value = Event(Unit)
            } catch (e: AuthException) {
                _emailState.value = Event(INCORRECT_ERROR)
                _passState.value = Event(INCORRECT_ERROR)

            } catch (e: UnknownHostException) {
                _toast.value = Event("Need internet connection")
            } finally {
                _state.value = DEFAULT_STATE
            }

        }
    }

    companion object {
        const val DEFAULT_STATE = 0
        const val LOADING_STATE = 1

        const val EMPTY_ERROR = 1
        const val INCORRECT_ERROR = 2
    }

}