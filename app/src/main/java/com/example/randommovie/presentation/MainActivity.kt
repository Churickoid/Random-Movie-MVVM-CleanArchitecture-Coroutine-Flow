package com.example.randommovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.randommovie.R
import com.example.randommovie.databinding.ActivityMainBinding
import com.example.randommovie.presentation.screen.list.ListFragment
import com.example.randommovie.presentation.screen.filter.FilterFragment
import com.example.randommovie.presentation.screen.movie.MovieFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController
        val destinationListener = NavController.OnDestinationChangedListener {_, destination, arguments ->
            supportActionBar?.title = destination.label
        }
        navController.addOnDestinationChangedListener(destinationListener)

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

    }
}