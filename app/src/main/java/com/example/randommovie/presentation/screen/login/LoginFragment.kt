package com.example.randommovie.presentation.screen.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentLoginBinding


class LoginFragment: Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)

        binding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_tabsFragment)
        }

    }
}



