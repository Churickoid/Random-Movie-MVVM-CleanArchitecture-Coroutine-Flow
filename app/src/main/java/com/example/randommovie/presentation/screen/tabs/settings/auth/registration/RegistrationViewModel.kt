package com.example.randommovie.presentation.screen.tabs.settings.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.data.DEFAULT_STATE
import com.example.randommovie.data.EmailExistException
import com.example.randommovie.data.INTERNET_ERROR
import com.example.randommovie.data.LOADING_STATE
import com.example.randommovie.domain.usecases.account.SignUpUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class RegistrationViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _emailState = MutableLiveData<Event<String>>()
    val emailState: LiveData<Event<String>> = _emailState

    private val _passState = MutableLiveData<Event<String>>()
    val passState: LiveData<Event<String>> = _passState

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int> = _state

    private val _startConfirmFragment = MutableLiveData<Event<Pair<String, String>>>()
    val startConfirmFragment: LiveData<Event<Pair<String, String>>> = _startConfirmFragment

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast

    private val emailPattern = Regex("[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+")

    fun checkForValidity(email: String, password: String, confirm: String) {
        if (!email.matches(emailPattern)) _emailState.value = Event(EMAIL_INVALID_ERROR)
        if (password != confirm) {
            _passState.value = Event(PASS_DIFFERENT_ERROR)
            return
        }
        if (password.length < PASS_LENGTH) {
            _passState.value = Event(PASS_LENGTH_ERROR)
            return
        }
        signUp(email, password)
    }

    private fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LOADING_STATE
            try {
                signUpUseCase(email, password)
                _startConfirmFragment.value = Event(Pair(email, password))
            } catch (e: EmailExistException) {
                _emailState.value = Event(EMAIL_EXIST_ERROR)
            } catch (e: UnknownHostException) {
                _toast.value = Event(INTERNET_ERROR)
            } finally {
                _state.value = DEFAULT_STATE
            }
        }
    }

    companion object {
        const val EMAIL_INVALID_ERROR = "Invalid email format"
        const val EMAIL_EXIST_ERROR = "Email already used"

        private const val PASS_LENGTH = 8

        const val PASS_DIFFERENT_ERROR = "Passwords must match"
        const val PASS_LENGTH_ERROR = "Passwords must be at least $PASS_LENGTH characters."
    }
}