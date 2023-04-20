package com.example.randommovie.presentation.screen.tabs.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentLoginBinding
import com.example.randommovie.presentation.screen.tabs.login.LoginViewModel.Companion.DEFAULT_STATE
import com.example.randommovie.presentation.screen.tabs.login.LoginViewModel.Companion.EMPTY_ERROR
import com.example.randommovie.presentation.screen.tabs.login.LoginViewModel.Companion.INCORRECT_ERROR
import com.example.randommovie.presentation.screen.tabs.login.LoginViewModel.Companion.LOADING_STATE
import com.example.randommovie.presentation.tools.factory

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)

        binding.signInButton.setOnClickListener {
            viewModel.signIn(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
        viewModel.emailState.observe(viewLifecycleOwner) {
            it.getValue()?.let { state ->
                binding.emailTextInput.error = errorMassageHandler(state)
            }
        }
        viewModel.passState.observe(viewLifecycleOwner) {
            it.getValue()?.let { state ->
                binding.passwordTextInput.error = errorMassageHandler(state)
            }
        }
        binding.passwordEditText.doOnTextChanged { _, _, _, _ ->
            binding.passwordTextInput.error = null
        }
        binding.emailEditText.doOnTextChanged { _, _, _, _ ->
            binding.emailTextInput.error = null
        }

        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                DEFAULT_STATE -> buttonStateHandler(true)
                LOADING_STATE -> buttonStateHandler(false)
            }
        }
        viewModel.toast.observe(viewLifecycleOwner){
            it.getValue()?.let { state ->
                Toast.makeText(requireContext(), state, Toast.LENGTH_SHORT).show()

            }
        }

        viewModel.startTabsFragment.observe(viewLifecycleOwner) {
            //findNavController().navigate(R.id.action_loginFragment_to_tabsFragment)
        }


    }

    private fun errorMassageHandler(state: Int): String {
        return when (state) {
            EMPTY_ERROR -> "Field is empty"
            INCORRECT_ERROR -> "Incorrect email or password"
            else -> throw IllegalArgumentException("Unknown state")
        }
    }

    private fun buttonStateHandler(buttonState: Boolean){
        binding.signInButton.isEnabled = buttonState
        binding.guestButton.isEnabled = buttonState
        binding.createAccountTextView.isEnabled = buttonState

        binding.loadingProgressBar.visibility = if (buttonState) View.INVISIBLE
        else View.VISIBLE

    }
}




