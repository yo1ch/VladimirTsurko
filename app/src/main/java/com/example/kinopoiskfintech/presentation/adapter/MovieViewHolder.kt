package com.example.kinopoiskfintech.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoiskfintech.databinding.MovieItemBinding
import com.example.kinopoiskfintech.domain.models.Movie

data class MovieViewHolder(val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root){
    fun bind(movie: Movie) {
        bindGenre(String.format("%s (%s)", movie.genres, movie.year))
        bindFavouriteState(movie.isFavourite)
        bindName(movie.nameRu)
        bindImage(movie.posterUrl)
    }

    fun bindFavouriteState(favourite: Boolean) {
        when (favourite) {
            true -> binding.starImage.visibility = View.VISIBLE
            false -> binding.starImage.visibility = View.INVISIBLE
        }
    }
    private fun bindName(name: String) {
        binding.tvName.text = name
    }
    private fun bindGenre(genre: String) {
        binding.tvGenre.text = genre
    }
    private fun bindImage(imageUrl: String) {
        Glide
            .with(binding.root)
            .load(imageUrl)
            .into(binding.posterImage)
    }
    }
