package com.example.randommovie.presentation.screen.tabs.settings.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentAuthTabsBinding
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

        val navHost =
            childFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController

        binding.authTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                navController.popBackStack()
                navController.navigate(
                    when (tab.position) {
                        0 -> R.id.registrationFragment
                        1 -> R.id.loginFragment
                        else -> throw IllegalArgumentException("Unknown tab")
                    }
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}