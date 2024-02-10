package com.spinoza.moviesforfintech.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("kinopoiskId")
    val filmId: Int,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("posterUrlPreview")
    val posterUrlPreview: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("countries")
    val countries:List<CountryDto>,
    @SerializedName("genres")
    val genres:List<GenreDto>,
)
