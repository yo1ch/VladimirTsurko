package com.example.kinopoiskfintech.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoiskfintech.databinding.MovieItemBinding
import com.example.kinopoiskfintech.domain.models.Movie
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

data class MovieViewHolder(val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
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
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
        Glide
            .with(binding.root)
            .load(imageUrl)
            .placeholder(shimmerDrawable)
            .into(binding.posterImage)
    }
}
