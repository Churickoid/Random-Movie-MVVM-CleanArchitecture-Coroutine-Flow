package com.example.randommovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.data.MovieInterfaceImpl
import com.example.randommovie.data.RetrofitMovieApiInterface
import com.example.randommovie.databinding.ActivityMainBinding
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.presentation.screen.FilterFragment
import com.example.randommovie.presentation.screen.ListFragment
import com.example.randommovie.presentation.screen.MovieFragment
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, MovieFragment())
                .commit()
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.movie -> replaceFragment(MovieFragment())
                R.id.filter -> replaceFragment(FilterFragment())
                R.id.list -> replaceFragment(ListFragment())
                else -> throw Exception("Invalid ID")
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }

}