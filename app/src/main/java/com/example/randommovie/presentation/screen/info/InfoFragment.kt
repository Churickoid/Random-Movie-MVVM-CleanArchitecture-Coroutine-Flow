package com.example.randommovie.presentation.screen.info

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentInfoBinding
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.presentation.screen.info.InfoViewModel.Companion.LOADING_STATE
import com.example.randommovie.presentation.screen.info.InfoViewModel.Companion.VALID_STATE
import com.example.randommovie.presentation.tools.factory

class InfoFragment : Fragment() {

    private val viewModel: InfoViewModel by viewModels { factory() }
    private lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)
        val movie = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> requireArguments().getParcelable(
                ARG_MOVIE, Movie::class.java
            ) ?: throw IllegalArgumentException("error")
            else -> @Suppress("DEPRECATION")
            requireArguments().getParcelable(
                ARG_MOVIE
            ) ?: throw IllegalArgumentException("error")
        }

        binding.retryButton.setOnClickListener {
            viewModel.getMovieInfo(movie.id)
        }
        viewModel.load.observe(viewLifecycleOwner){
            it.getValue()?.let { viewModel.getMovieInfo(movie.id) }
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                LOADING_STATE -> changeState(View.VISIBLE, View.INVISIBLE, View.INVISIBLE)
                VALID_STATE -> changeState(View.INVISIBLE, View.VISIBLE, View.INVISIBLE)
                else -> {
                    changeState(View.INVISIBLE, View.INVISIBLE, View.VISIBLE)
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.movieInfo.observe(viewLifecycleOwner) {
            val secondTitle: String
            val firstTitle = if (movie.titleRu == null) {
                secondTitle = "—"
                movie.title
            } else {
                secondTitle = movie.title ?: "—"
                movie.titleRu
            }
            binding.titleMainTextView.text = firstTitle
            binding.titleExtraTextView.text = secondTitle
            binding.yearTextView.text = movie.releaseDate.toString()
            binding.genreTextView.text = movie.genre.joinToString(separator = ", ")
            binding.countryTextView.text = movie.country.joinToString(separator = ", ")
            binding.lengthTextView.text = parseTimeToString(it.length)
            binding.kinopoiskTextView.text = movie.ratingKP?.toString() ?: " — "
            binding.imdbTextView.text = movie.ratingIMDB?.toString() ?: " — "

            binding.detailsButton.setOnClickListener { TODO() }

            binding.descriptionTextView.text = it.description ?: "—"
            if (it.headerURL != null) {
                Glide.with(this@InfoFragment)
                    .load(it.headerURL)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(binding.headerImageView)
            } else {
                Glide.with(this@InfoFragment)
                    .load(movie.posterUrl)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(binding.headerImageView)

            }
        }


    }

    private fun changeState(loading: Int, info: Int, error: Int) {
        binding.loadingProgressBar.visibility = loading
        binding.infoLayout.visibility = info
        binding.retryButton.visibility = error
    }

    private fun parseTimeToString(time: Int?): String {
        if (time == null) return " — "
        var string = ""
        if (time > 60) string += "${time / 60}:${time % 60} / "
        string += "$time min"
        return string
    }

    companion object {
        const val ARG_MOVIE = "ARG_MOVIE"
    }
}