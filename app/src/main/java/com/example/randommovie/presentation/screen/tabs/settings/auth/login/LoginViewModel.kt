package com.example.randommovie.presentation.screen.tabs.settings.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.data.AuthException
import com.example.randommovie.data.DEFAULT_STATE
import com.example.randommovie.data.INTERNET_ERROR
import com.example.randommovie.data.LOADING_STATE
import com.example.randommovie.domain.usecases.account.SignInUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class LoginViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _emailState = MutableLiveData<Event<String>>()
    val emailState: LiveData<Event<String>> = _emailState

    private val _passState = MutableLiveData<Event<String>>()
    val passState: LiveData<Event<String>> = _passState

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int> = _state

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast

    private val _closeFragment = MutableLiveData<Event<Unit>>()
    val closeFragment = _closeFragment

    fun checkForValidity(email:String,password:String){
        if (email.isEmpty()) _emailState.value = Event(EMPTY_ERROR)
        if (password.isEmpty()) _passState.value = Event(EMPTY_ERROR)
        if (email.isEmpty() || password.isEmpty()) return
        signIn(email,password)
    }
    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                _state.value = LOADING_STATE
                signInUseCase(email, password)
                _toast.value = Event(SUCCESS)
                _closeFragment.value = Event(Unit)
            } catch (e: AuthException) {
                _emailState.value = Event(INCORRECT_ERROR)
                _passState.value = Event(INCORRECT_ERROR)

            } catch (e: UnknownHostException) {
                _toast.value = Event(INTERNET_ERROR)
            } finally {
                _state.value = DEFAULT_STATE
            }

        }
    }


    companion object {

        const val SUCCESS = "Account successfully added"

        private const val EMPTY_ERROR = "Field is empty"
        private const val INCORRECT_ERROR = "Incorrect email or password"
    }

}