package com.example.kinopoiskfintech.presentation.adapter

import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
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

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val film = getItem(position)
        with(holder.binding) {
            Glide.with(root).load(film.posterUrlPreview).into(posterImage)
            tvName.text = film.nameRu
            tvGenre.text = String.format("%s (%s)", film.genres, film.year)
            when (film.isFavourite) {
                true -> starImage.visibility = VISIBLE
                false -> starImage.visibility = INVISIBLE
            }
            root.setOnClickListener { onFilmItemClickListener?.invoke(film.filmId) }
            root.setOnLongClickListener {
                onFilmItemLongClickListener?.invoke(film)
                true
            }
        }
    }
}