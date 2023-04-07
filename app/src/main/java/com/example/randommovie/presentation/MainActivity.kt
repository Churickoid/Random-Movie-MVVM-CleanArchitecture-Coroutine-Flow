package com.example.randommovie.presentation

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.randommovie.R
import com.example.randommovie.databinding.ActivityMainBinding
import com.example.randommovie.presentation.tools.AppBarActions


class MainActivity : AppCompatActivity(), AppBarActions {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.filterFragment,R.id.listFragment,R.id.movieFragment))
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun changeTitle(label: String) {
        supportActionBar?.title = label
    }

    override fun changeColor(color: Int, colorLight: Int, colorDark: Int) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
        window.statusBarColor = colorDark
        binding.bottomNavigation.itemIconTintList = ColorStateList.valueOf(color)
        binding.bottomNavigation.itemTextColor = ColorStateList.valueOf(color)
    }
}