package com.example.randommovie.presentation.screen.tabs.settings.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentRegistrationBinding
import com.example.randommovie.presentation.screen.BaseFragment.Companion.DEFAULT_STATE
import com.example.randommovie.presentation.screen.BaseFragment.Companion.LOADING_STATE
import com.example.randommovie.presentation.tools.factory

class RegistrationFragment : Fragment() {

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

        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                DEFAULT_STATE -> buttonStateHandler(true)
                LOADING_STATE -> buttonStateHandler(false)
            }
        }


    }

    private fun buttonStateHandler(buttonState: Boolean){
        binding.signUpButton.isEnabled = buttonState

        binding.loadingProgressBar.visibility = if (buttonState) View.INVISIBLE
        else View.VISIBLE

    }


}

