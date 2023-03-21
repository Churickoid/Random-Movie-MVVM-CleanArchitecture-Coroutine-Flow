package com.example.randommovie.presentation.screen.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.databinding.ItemMovieBinding
import com.example.randommovie.domain.entity.UserInfoAndMovie
import com.example.randommovie.presentation.screen.getRatingColor
import com.example.randommovie.presentation.screen.list.MovieListAdapter.MovieViewHolder

class MovieListAdapter(private val listener: ItemListener) :
    ListAdapter<UserInfoAndMovie, MovieViewHolder>(ItemCallback), View.OnClickListener {


    override fun onClick(view: View) {
        val userInfo = view.tag as UserInfoAndMovie
        when (view.id) {
            R.id.moreButton -> showPopupMenu(view)
            else -> listener.onChooseMovie(userInfo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)

        binding.moreButton.setOnClickListener(this)
        binding.root.setOnClickListener(this)

        return MovieViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val userInfo = getItem(position)
        val movie = userInfo.movie
        val context = holder.itemView.context
        val rating = when {
            movie.ratingKP > 0.0 && movie.ratingIMDB > 0.0 -> (movie.ratingKP + movie.ratingIMDB) / 2
            movie.ratingKP > 0.0 -> movie.ratingKP
            else -> movie.ratingIMDB
        }

        with(holder.binding) {
            root.tag = userInfo
            moreButton.tag = userInfo

            val year = movie.year?.toString() ?: " — "
            titleMainTextView.text = "${movie.titleMain} ($year)"
            titleExtraTextView.text = movie.titleSecond
            countryTextView.text = movie.country.joinToString(", ")
            genresTextView.text = movie.genre.joinToString(", ")
            ratingTextView.text = if (rating == 0.0) " — "
            else String.format("%.1f", rating)
            ratingTextView.setTextColor(getRatingColor(rating, context))

            if (userInfo.userRating == 0) {
                yourRatingTextView.visibility = View.INVISIBLE
                bookmarkImageView.visibility = View.VISIBLE
            } else {
                bookmarkImageView.visibility = View.INVISIBLE
                yourRatingTextView.visibility = View.VISIBLE
                yourRatingTextView.text = userInfo.userRating.toString()
                yourRatingTextView.background.setTint(
                    getRatingColor(
                        userInfo.userRating.toDouble(),
                        context
                    )
                )
            }

            Glide.with(holder.itemView)
                .load(movie.posterUrl)
                .into(posterImageView)


        }

    }

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<UserInfoAndMovie>() {
        override fun areItemsTheSame(
            oldItem: UserInfoAndMovie,
            newItem: UserInfoAndMovie
        ): Boolean {
            return oldItem.movie.id == oldItem.movie.id
        }

        override fun areContentsTheSame(
            oldItem: UserInfoAndMovie,
            newItem: UserInfoAndMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemListener {
        fun onChooseMovie(infoAndMovie: UserInfoAndMovie)
        fun onDeleteMovie(infoAndMovie: UserInfoAndMovie)
        fun onChangeInfo(infoAndMovie: UserInfoAndMovie)
    }

    private fun showPopupMenu(view: View) {
        val userInfo = view.tag as UserInfoAndMovie
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.setForceShowIcon(true)
        popupMenu.inflate(R.menu.popup_item_actions)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.star -> {
                    listener.onChangeInfo(userInfo)
                    true
                }
                R.id.delete -> {
                    listener.onDeleteMovie(userInfo)
                    true
                }
                else -> true
            }
        }
        popupMenu.show()
    }


}