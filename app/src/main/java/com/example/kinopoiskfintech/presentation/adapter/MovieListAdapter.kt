package com.example.kinopoiskfintech.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kinopoiskfintech.databinding.MovieItemBinding
import com.example.kinopoiskfintech.domain.models.Movie
import javax.inject.Inject

class MovieListAdapter @Inject constructor() :
    ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {

    var onFilmItemLongClickListener: ((Movie) -> Unit)? = null
    var onFilmItemClickListener: ((Int) -> Unit)? = null
    var onReachEndListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (val latestPayload = payloads.lastOrNull()) {
            is FavouriteChangePayload -> holder.bindFavouriteState(latestPayload.isFavourite)
            else -> onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            holder.bind(movie)
            root.setOnClickListener {
                onFilmItemClickListener?.invoke(movie.filmId)
            }
            root.setOnLongClickListener {
                onFilmItemLongClickListener?.invoke(movie)
                true
            }
            if (position >= itemCount - 10) {
                onReachEndListener?.invoke()
            }
        }
    }
}