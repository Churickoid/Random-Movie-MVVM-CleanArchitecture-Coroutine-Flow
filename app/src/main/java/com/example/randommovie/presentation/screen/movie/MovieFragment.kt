package com.example.randommovie.presentation.screen.movie

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.data.MovieRepositoryImpl
import com.example.randommovie.databinding.FragmentMovieBinding
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.presentation.App
import com.example.randommovie.presentation.screen.factory
import kotlinx.coroutines.launch

class MovieFragment:Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel: MovieViewModel by viewModels{factory()}
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

        binding.nextMovieButton.setOnClickListener{
            viewModel.getRandomMovie()
        }

        viewModel.movie.observe(viewLifecycleOwner){
            binding.titleMainTextView.text = it.titleRu ?: it.title
            binding.titleExtraTextView.text = it.title ?: ""
            val genre = it.genre ?: ""
            binding.genresTextView.text = "Жанр: $genre"
            binding.kinopoiskRateTextView.text = it.ratingKP?.toString() ?: ""
            binding.imdbRateTextView.text = it.ratingIMDB?.toString() ?: ""
            if (it.poster != null) {
                Glide.with(this@MovieFragment)
                    .load(it.poster)
                    .skipMemoryCache(true)
                    .into(binding.posterImageView)
            }
            else{
                binding.posterImageView.setImageResource(R.drawable.blanked_image)
            }
        }

    }
}