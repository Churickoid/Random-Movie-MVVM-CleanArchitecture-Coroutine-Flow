package com.example.randommovie.presentation.screen.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randommovie.databinding.ItemMovieBinding
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.UserInfoAndMovie
import com.example.randommovie.presentation.screen.getRatingColor

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {


    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    var userInfoAndMovieList = listOf<UserInfoAndMovie>()
        set(it) {
            field = it
            notifyDataSetChanged()
        }
    var onItemClickListener: ((Movie) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = userInfoAndMovieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val userInfo = userInfoAndMovieList[position]
        val movie = userInfo.movie
        val context = holder.itemView.context
        val rating = when{
            movie.ratingKP > 0.0 && movie.ratingIMDB > 0.0-> (movie.ratingKP + movie.ratingIMDB) / 2
            movie.ratingKP > 0.0 -> movie.ratingKP
            else -> movie.ratingIMDB
        }
        with(holder.binding) {
            titleMainTextView.text = "${movie.titleMain} (${movie.year})"
            titleExtraTextView.text = movie.titleSecond
            countryTextView.text = movie.country.joinToString(", ")
            genresTextView.text = movie.genre.joinToString(", ")
            ratingTextView.text = if (rating == 0.0) " — "
            else String.format("%.1f", rating)
            ratingTextView.setTextColor(getRatingColor(rating,context))

            yourRatingTextView.text = userInfo.userRating.toString()
            yourRatingTextView.background.setTint(getRatingColor(userInfo.userRating.toDouble(),context))

            if (userInfo.inWatchlist) bookmarkImageView.visibility = View.VISIBLE
            else bookmarkImageView.visibility = View.INVISIBLE

            Glide.with(holder.itemView)
                .load(movie.posterUrl)
                .skipMemoryCache(true)
                .into(posterImageView)

            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(movie)
            }

        }

    }



}