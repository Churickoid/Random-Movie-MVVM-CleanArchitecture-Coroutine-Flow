package com.churickoid.filmity.presentation.screen.tabs.settings.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.churickoid.filmity.R
import com.churickoid.filmity.data.DEFAULT_STATE
import com.churickoid.filmity.data.LOADING_STATE
import com.churickoid.filmity.databinding.FragmentRegistrationBinding
import com.churickoid.filmity.presentation.screen.BaseFragment
import com.churickoid.filmity.presentation.screen.tabs.settings.auth.registration.ConfirmFragment.Companion.ARG_EMAIL
import com.churickoid.filmity.presentation.screen.tabs.settings.auth.registration.ConfirmFragment.Companion.ARG_PASS
import com.churickoid.filmity.presentation.tools.factory
import kotlinx.coroutines.launch

class RegistrationFragment : BaseFragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)

        binding.signUpButton.setOnClickListener {
            viewModel.checkForValidity(
                binding.emailEditText.text?.trim().toString(),
                binding.passwordEditText.text.toString(),
                binding.confirmEditText.text.toString()
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
                binding.confirmTextInput.error = massage
            }
        }
        binding.emailEditText.doOnTextChanged { _, _, _, _ ->
            binding.emailTextInput.error = null
            binding.emailTextInput.isErrorEnabled = false
        }
        binding.passwordEditText.doOnTextChanged { _, _, _, _ ->
            binding.passwordTextInput.error = null
            binding.passwordTextInput.isErrorEnabled = false
        }
        binding.confirmEditText.doOnTextChanged { _, _, _, _ ->
            binding.confirmTextInput.error = null
            binding.confirmTextInput.isErrorEnabled = false
        }

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                DEFAULT_STATE -> buttonStateHandler(true)
                LOADING_STATE -> buttonStateHandler(false)
            }
        }
        viewModel.startConfirmFragment.observe(viewLifecycleOwner) {
            it.getValue()?.let { (email, pass) ->
                findNavController().navigate(
                    R.id.action_registrationFragment_to_confirmFragment,
                    bundleOf(
                        ARG_EMAIL to email,
                        ARG_PASS to pass
                    )
                )
            }
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            toastError(it)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect { colorMain ->
                changeTextInputLayoutColor(binding.emailTextInput,colorMain)
                changeTextInputLayoutColor(binding.passwordTextInput,colorMain)
                changeTextInputLayoutColor(binding.confirmTextInput,colorMain)
                binding.signUpButton.setBackgroundColor(colorMain)

            }
        }

    }

    private fun buttonStateHandler(buttonState: Boolean) {
        binding.signUpButton.isEnabled = buttonState

        binding.loadingProgressBar.visibility = if (buttonState) View.INVISIBLE
        else View.VISIBLE

    }


}

