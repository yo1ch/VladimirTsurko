package com.example.kinopoiskfintech.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskfintech.domain.models.Movie

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}