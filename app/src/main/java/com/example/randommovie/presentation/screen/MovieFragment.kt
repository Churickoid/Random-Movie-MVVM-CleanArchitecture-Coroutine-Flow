package com.example.randommovie.presentation.screen

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.data.MovieRepositoryImpl
import com.example.randommovie.databinding.FragmentMovieBinding
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.presentation.App
import kotlinx.coroutines.launch

class MovieFragment:Fragment() {

    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)



        val test = App().container.movieRepository

        binding.nextMovieButton.setOnClickListener {
            lifecycleScope.launch {
                val movie = test.getRandomMovie(SearchFilter())
                binding.titleMainTextView.text = movie.titleRu ?: movie.title
                binding.titleExtraTextView.text = movie.title ?: ""
                val genre = movie.genre ?: ""
                binding.genresTextView.text = "Жанр: $genre"
                binding.kinopoiskRateTextView.text = movie.ratingKP?.toString() ?: ""
                binding.imdbRateTextView.text = movie.ratingIMDB?.toString() ?: ""
                if (movie.poster != null) {
                    Glide.with(this@MovieFragment)
                        .load(movie.poster)
                        .skipMemoryCache(true)
                        .into(binding.posterImageView)
                }
                else{
                    binding.posterImageView.setImageResource(R.drawable.blanked_image)
                }
            }

        }
    }
}