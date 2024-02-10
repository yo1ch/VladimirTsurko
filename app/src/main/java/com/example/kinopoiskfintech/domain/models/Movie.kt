package com.example.kinopoiskfintech.domain.models

data class Movie(
    val filmId: Int,
    val nameRu: String,
    val year: Int,
    val countries: String,
    val genres: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val description: String,
    var isFavourite: Boolean = false,
)
