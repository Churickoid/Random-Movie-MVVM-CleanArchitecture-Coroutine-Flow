package com.example.randommovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.data.MovieInterfaceImpl
import com.example.randommovie.data.RetrofitMovieApiInterface
import com.example.randommovie.databinding.ActivityMainBinding
import com.example.randommovie.domain.entity.SearchFilter
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

    }

    companion object{
        const val IMAGE_URL_BASE = "https://kinopoiskapiunofficial.tech"
    }
}