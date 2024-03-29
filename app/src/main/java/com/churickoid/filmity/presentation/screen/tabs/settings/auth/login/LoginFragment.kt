package com.churickoid.filmity.presentation.screen.tabs.settings.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.churickoid.filmity.R
import com.churickoid.filmity.data.DEFAULT_STATE
import com.churickoid.filmity.data.LOADING_STATE
import com.churickoid.filmity.databinding.FragmentLoginBinding
import com.churickoid.filmity.presentation.screen.BaseFragment
import com.churickoid.filmity.presentation.tools.factory
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment() {
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
            viewModel.checkForValidity(
                binding.emailEditText.text?.trim().toString(),
                binding.passwordEditText.text.toString()
            )
        }
        viewModel.emailState.observe(viewLifecycleOwner) {
            it.getValue()?.let { massage ->
                binding.emailTextInput.error = massage
            }
        }
        viewModel.passState.observe(viewLifecycleOwner) {
            it.getValue()?.let { massage ->
                binding.passwordTextInput.error = massage
            }
        }
        binding.passwordEditText.doOnTextChanged { _, _, _, _ ->
            binding.passwordTextInput.error = null
            binding.passwordTextInput.isErrorEnabled = false

        }
        binding.emailEditText.doOnTextChanged { _, _, _, _ ->
            binding.emailTextInput.error = null
            binding.emailTextInput.isErrorEnabled = false
        }

        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                DEFAULT_STATE -> buttonStateHandler(true)
                LOADING_STATE -> buttonStateHandler(false)
            }
        }
        viewModel.toast.observe(viewLifecycleOwner){
            toastError(it)
        }

        viewModel.closeFragment.observe(viewLifecycleOwner) {
            val parentNavFragment = requireParentFragment().requireParentFragment()
            parentNavFragment.findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect { colorMain ->
                changeTextInputLayoutColor(binding.emailTextInput,colorMain)
                changeTextInputLayoutColor(binding.passwordTextInput,colorMain)
                binding.signInButton.setBackgroundColor(colorMain)
            }
        }


    }


    private fun buttonStateHandler(buttonState: Boolean){
        binding.signInButton.isEnabled= buttonState

        binding.loadingProgressBar.visibility = if (buttonState) View.INVISIBLE
        else View.VISIBLE

    }
}




