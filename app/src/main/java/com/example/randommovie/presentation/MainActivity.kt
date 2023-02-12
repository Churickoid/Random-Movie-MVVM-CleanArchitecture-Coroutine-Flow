package com.example.randommovie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.data.MovieInterfaceImpl
import com.example.randommovie.data.RetrofitMovieApiInterface
import com.example.randommovie.databinding.ActivityMainBinding
import com.example.randommovie.domain.entity.SearchOption
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

        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitMovieApiInterface::class.java)

        val test = MovieInterfaceImpl(retrofit)

        binding.nextMovieButton.setOnClickListener {
            lifecycleScope.launch {


                val movie = test.getRandomMovie(SearchOption())
                Log.e("!!!!", movie.toString())
                binding.titleTextView.text = movie.titleRu ?: movie.title
                if (movie.poster != null) {
                    Glide.with(this@MainActivity)
                        .load(movie.poster)
                        .into(binding.posterImageView)
                }
                else{
                    binding.posterImageView.setImageResource(R.drawable.blanked_image)
                }
            }

        }
    }
    companion object{
        const val IMAGE_URL_BASE = "https://kinopoiskapiunofficial.tech"
    }
}