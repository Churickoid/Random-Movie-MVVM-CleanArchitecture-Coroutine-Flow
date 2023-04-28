package com.example.randommovie.presentation.screen.tabs.settings.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.data.EmailExistException
import com.example.randommovie.domain.usecases.account.ConfirmRegistrationUseCase
import com.example.randommovie.domain.usecases.account.SignUpUseCase
import com.example.randommovie.presentation.screen.BaseFragment.Companion.DEFAULT_STATE
import com.example.randommovie.presentation.screen.BaseFragment.Companion.LOADING_STATE
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val confirmRegistrationUseCase: ConfirmRegistrationUseCase
) : ViewModel() {

    private val _emailState = MutableLiveData<Event<String>>()
    val emailState: LiveData<Event<String>> = _emailState

    private val _passState = MutableLiveData<Event<String>>()
    val passState: LiveData<Event<String>> = _passState

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int> = _state

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
            } catch (e: EmailExistException) {
                _emailState.value = Event(EMAIL_EXIST_ERROR)
            } finally {
                _state.value = DEFAULT_STATE
            }

            //TODO("check mail")
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