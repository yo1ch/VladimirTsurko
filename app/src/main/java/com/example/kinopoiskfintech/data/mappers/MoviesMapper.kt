package com.example.kinopoiskfintech.data.mappers

import com.example.kinopoiskfintech.data.local.models.MovieDbModel
import com.example.kinopoiskfintech.data.local.models.MovieDescriptionDbModel
import com.example.kinopoiskfintech.domain.models.Movie
import com.spinoza.moviesforfintech.data.network.model.CountryDto
import com.spinoza.moviesforfintech.data.network.model.GenreDto
import com.spinoza.moviesforfintech.data.network.model.MovieDto
import com.spinoza.moviesforfintech.data.network.model.MoviePreviewDto
import java.util.stream.Collectors


fun MovieDto.toModel() = Movie(
        filmId = filmId,
        nameRu = nameRu,
        year = year,
        countries = countriesToString(countries),
        genres = genresToString(genres),
        posterUrl =posterUrl,
        posterUrlPreview =posterUrlPreview,
        description =description,
        isFavourite =true,
    )

fun MovieDto.toDescriptionDnModel() = MovieDescriptionDbModel(
    filmId = filmId,
    nameRu = nameRu,
    year = year,
    countries = countriesToString(countries),
    genres = genresToString(genres),
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    description = description,
)

fun Movie.toDbModel(): MovieDbModel = MovieDbModel(
    filmId = filmId,
    nameRu = nameRu,
    year = year,
    countries = countries,
    genres = genres,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    isFavorite = isFavourite,
)

fun MoviePreviewDto.toDbModel(): MovieDbModel = MovieDbModel(
    filmId = filmId,
    nameRu = nameRu,
    year = year,
    countries = countriesToString(countries),
    genres = genresToString(genres),
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    isFavorite = false
)

fun MovieDbModel.toModel(): Movie = Movie(
    filmId = filmId,
    nameRu = nameRu,
    year = year,
    countries = countries,
    genres = genres,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    description = "",
    isFavourite = isFavorite
)

fun MovieDescriptionDbModel.toModel(): Movie = Movie(
    filmId = filmId,
    nameRu = nameRu,
    year = year,
    countries = countries,
    genres = genres,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    description = description,
)
private fun countriesToString(
    countriesDto: List<CountryDto>
): String =
    countriesDto.map {
        it.country
    }
        .stream()
        .collect(Collectors.joining(", "))

private fun genresToString(
    genresDto: List<GenreDto>
): String =
    genresDto.map {
        it.genre
    }
        .stream()
        .collect(Collectors.joining(", "))
