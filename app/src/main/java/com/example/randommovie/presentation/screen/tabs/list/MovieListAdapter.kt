package com.example.randommovie.presentation.screen.tabs.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.databinding.ItemMovieBinding
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.presentation.screen.BaseFragment.Companion.getRatingColor
import com.example.randommovie.presentation.screen.tabs.list.MovieListAdapter.MovieViewHolder

class MovieListAdapter(private val listener: ItemListener, private val color: Int) :
    ListAdapter<ActionsAndMovie, MovieViewHolder>(ItemCallback), View.OnClickListener {


    override fun onClick(view: View) {
        val userInfo = view.tag as ActionsAndMovie
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
        binding.bookmarkImageView.drawable.setTint(color)
        binding.moreButton.drawable.setTint(color)

        return MovieViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val userInfo = getItem(position)
        val movie = userInfo.movie
        val context = holder.itemView.context
        val rating = movie.ratingKP

        with(holder.binding) {
            root.tag = userInfo
            moreButton.tag = userInfo

            val year = movie.year?.toString() ?: " — "
            titleMainTextView.text = context.getString(R.string.movie_with_year, movie.titleMain,year)
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

    object ItemCallback : DiffUtil.ItemCallback<ActionsAndMovie>() {
        override fun areItemsTheSame(
            oldItem: ActionsAndMovie,
            newItem: ActionsAndMovie
        ): Boolean {
            return oldItem.movie.id == oldItem.movie.id
        }

        override fun areContentsTheSame(
            oldItem: ActionsAndMovie,
            newItem: ActionsAndMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemListener {
        fun onChooseMovie(infoAndMovie: ActionsAndMovie)
        fun onDeleteMovie(infoAndMovie: ActionsAndMovie)
        fun onChangeInfo(infoAndMovie: ActionsAndMovie)
    }

    private fun showPopupMenu(view: View) {
        val userInfo = view.tag as ActionsAndMovie
        val popupMenu = PopupMenu(ContextThemeWrapper(view.context,R.style.popupStyle), view)
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