package com.example.randommovie.presentation.screen.tabs

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentTabsBinding
import com.example.randommovie.presentation.screen.BaseFragment
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
            baseViewModel.color.collect { (colorMain, colorBack) ->
                binding.bottomNavigation.itemIconTintList = ColorStateList.valueOf(colorMain)
                binding.bottomNavigation.itemTextColor = ColorStateList.valueOf(colorMain)
                binding.bottomNavigation.itemRippleColor = ColorStateList.valueOf(colorBack)
            }
        }
    }


}

