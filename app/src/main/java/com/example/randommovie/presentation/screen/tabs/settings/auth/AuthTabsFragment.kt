package com.example.randommovie.presentation.screen.tabs.settings.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentAuthTabsBinding
import com.example.randommovie.presentation.screen.tabs.settings.auth.login.LoginFragment
import com.example.randommovie.presentation.screen.tabs.settings.auth.registration.RegistrationFragment
import com.google.android.material.tabs.TabLayout

class AuthTabsFragment : Fragment() {

    private lateinit var binding: FragmentAuthTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthTabsBinding.bind(view)

        binding.authTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                childFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainer, when (tab.position) {
                            0 -> RegistrationFragment()
                            1 -> LoginFragment()
                            else -> throw IllegalArgumentException("Unknown tab")
                        }
                    )
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}