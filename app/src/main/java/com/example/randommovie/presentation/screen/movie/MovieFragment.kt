package com.example.randommovie.presentation.screen.movie

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.data.MovieRepositoryImpl
import com.example.randommovie.databinding.FragmentMovieBinding
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.presentation.App
import com.example.randommovie.presentation.screen.factory
import com.example.randommovie.presentation.screen.filter.FilterViewModel
import kotlinx.coroutines.launch

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel: MovieViewModel by viewModels { factory() }
    private val filterViewModel: FilterViewModel by activityViewModels{ factory() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        binding.nextMovieButton.setOnClickListener {
            viewModel.getRandomMovie()
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            val year = it.releaseDate ?: "—"
            val secondTitle: String
            val firstTitle = if (it.titleRu == null) {
                secondTitle = "—"
                "${it.title} ($year)"
            } else {
                secondTitle = it.title ?: "—"
                "${it.titleRu} ($year)"
            }

            binding.titleMainTextView.text = firstTitle
            binding.titleExtraTextView.text = secondTitle
            binding.genresTextView.text = parseListToString(it.genre)
            binding.kinopoiskRateTextView.text = it.ratingKP?.toString() ?: " — "
            binding.imdbRateTextView.text = it.ratingIMDB?.toString() ?: " — "
            if (it.poster != null) {
                Glide.with(this@MovieFragment)
                    .load(it.poster)
                    .skipMemoryCache(true)
                    .into(binding.posterImageView)
            } else {
                binding.posterImageView.setImageResource(R.drawable.blanked_image)
            }
        }

        viewModel.buttonState.observe(viewLifecycleOwner){
            if(it) changeStateButton(View.VISIBLE,View.INVISIBLE)
            else changeStateButton(View.INVISIBLE,View.VISIBLE)

        }

    }
    private fun parseListToString(list: List<String>):String{
        var string = ""
        for(i in list.take(3)){
            string += "$i, "
        }
        return string.dropLast(2)
    }

    private fun changeStateButton(buttonState:Int, progressState:Int){
        binding.actionsGroup.visibility = buttonState
        binding.loadingProgressBar.visibility = progressState
    }
}