package com.spinoza.moviesforfintech.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviePreviewDto(
    @SerializedName("filmId")
    val filmId: Int,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("nameEn")
    val nameEn: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("filmLength")
    val filmLength: String,
    @SerializedName("countries")
    val countries: List<CountryDto>,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("ratingVoteCount")
    val ratingVoteCount: Int,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("posterUrlPreview")
    val posterUrlPreview: String,
)