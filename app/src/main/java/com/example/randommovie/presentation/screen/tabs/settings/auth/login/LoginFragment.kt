package com.example.randommovie.presentation.screen.tabs.settings.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentLoginBinding
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.tools.factory

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
            it.getValue()?.let { state ->
                Toast.makeText(requireContext(), state, Toast.LENGTH_SHORT).show()

            }
        }

        viewModel.closeFragment.observe(viewLifecycleOwner) {
            val parentNavFragment = requireParentFragment().requireParentFragment()
            parentNavFragment.findNavController().popBackStack()
        }


    }


    private fun buttonStateHandler(buttonState: Boolean){
        binding.signInButton.isEnabled= buttonState

        binding.loadingProgressBar.visibility = if (buttonState) View.INVISIBLE
        else View.VISIBLE

    }
}




