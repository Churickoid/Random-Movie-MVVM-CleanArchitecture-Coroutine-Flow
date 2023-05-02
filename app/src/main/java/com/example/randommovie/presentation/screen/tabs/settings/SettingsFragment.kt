package com.example.randommovie.presentation.screen.tabs.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentSettingsBinding
import com.example.randommovie.domain.entity.Token
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.tools.factory
import kotlinx.coroutines.launch

class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels { factory() }

    private var tokenList = mutableListOf<Token>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)


        binding.createTokenButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_authTabsFragment)
        }

        binding.tokenSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, position: Int, p3: Long
            ) {
                viewModel.setToken(tokenList[position], position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }


        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect { colorMain ->
                binding.createTokenButton.setBackgroundColor(colorMain)

                val adapter = TokenSpinnerAdapter(tokenList,colorMain ) {
                    viewModel.deleteToken(it)
                }
                binding.tokenSpinner.adapter = adapter

                viewModel.tokenList.observe(viewLifecycleOwner) {
                    tokenList.clear()
                    tokenList.addAll(it)
                    adapter.notifyDataSetChanged()
                    binding.tokenSpinner.setSelection(viewModel.getSpinnerPosition())
                }
            }
        }

    }
}