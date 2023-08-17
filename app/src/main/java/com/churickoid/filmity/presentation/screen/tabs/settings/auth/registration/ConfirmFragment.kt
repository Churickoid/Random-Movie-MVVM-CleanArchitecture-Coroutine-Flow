package com.churickoid.filmity.presentation.screen.tabs.settings.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.churickoid.filmity.R
import com.churickoid.filmity.databinding.FragmentConfirmBinding
import com.churickoid.filmity.presentation.screen.BaseFragment
import com.churickoid.filmity.presentation.tools.factory
import kotlinx.coroutines.launch

class ConfirmFragment : BaseFragment() {

    private val viewModel: ConfirmViewModel by viewModels { factory() }
    private lateinit var binding: FragmentConfirmBinding

    private val email: String
        get() = requireArguments().getString(ARG_EMAIL)
            ?: throw IllegalArgumentException("Can't work without email")

    private val password: String
        get() = requireArguments().getString(ARG_PASS)
            ?: throw IllegalArgumentException("Can't work without password")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmBinding.bind(view)

        binding.confirmButton.setOnClickListener {
            val codeStr = binding.codeEditText.text.toString()
            val code = if (codeStr.isEmpty()) 0 else codeStr.toInt()
            viewModel.confirmRegistration(email, password, code)
        }

        viewModel.closeFragment.observe(viewLifecycleOwner) {
            val parentNavFragment = requireParentFragment().requireParentFragment()
            parentNavFragment.findNavController().popBackStack()
        }

        viewModel.codeState.observe(viewLifecycleOwner) {
            it.getValue()?.let { massage ->
                binding.codeTextInput.error = massage
            }
        }
        viewModel.toast.observe(viewLifecycleOwner) {
            toastError(it)
        }


        binding.codeEditText.doOnTextChanged { _, _, _, _ ->
            binding.codeTextInput.error = null
            binding.codeTextInput.isErrorEnabled = false
        }

        viewLifecycleOwner.lifecycleScope.launch{
            baseViewModel.color.collect { colorMain ->
                changeTextInputLayoutColor(binding.codeTextInput,colorMain)
                binding.confirmButton.setBackgroundColor(colorMain)
            }
        }
    }


    companion object {
        const val ARG_EMAIL = "ARG_EMAIL"
        const val ARG_PASS = "ARG_PASS"

    }


}