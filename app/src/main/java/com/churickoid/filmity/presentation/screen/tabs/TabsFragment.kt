package com.churickoid.filmity.presentation.screen.tabs

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.churickoid.filmity.R
import com.churickoid.filmity.databinding.FragmentTabsBinding
import com.churickoid.filmity.presentation.screen.BaseFragment
import kotlinx.coroutines.launch

class TabsFragment:BaseFragment() {

    private lateinit var binding: FragmentTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabsBinding.bind(view)
        val navHost = childFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect { colorMain->
                val colorState = ColorStateList.valueOf(colorMain)
                binding.bottomNavigation.itemIconTintList = colorState
                binding.bottomNavigation.itemTextColor = colorState
            }
        }
    }


}

