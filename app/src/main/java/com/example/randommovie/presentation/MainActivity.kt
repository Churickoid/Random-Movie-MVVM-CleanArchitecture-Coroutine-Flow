package com.example.randommovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.randommovie.R
import com.example.randommovie.databinding.ActivityMainBinding
import com.example.randommovie.presentation.screen.list.ListFragment
import com.example.randommovie.presentation.screen.filter.FilterFragment
import com.example.randommovie.presentation.screen.movie.MovieFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val movieFragment = MovieFragment()
    private val filterFragment = FilterFragment()
    private val listFragment = ListFragment()

    private var activeFragment: Fragment = movieFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.movie -> replaceFragment(movieFragment)
                R.id.filter -> replaceFragment(filterFragment)
                R.id.list -> replaceFragment(listFragment)
                else -> throw Exception("Invalid ID")
            }
            return@setOnItemSelectedListener true
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()
        activeFragment = fragment
    }

}