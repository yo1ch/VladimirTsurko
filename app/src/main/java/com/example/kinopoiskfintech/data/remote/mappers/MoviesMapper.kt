package com.example.kinopoiskfintech.data.remote.mappers

import com.example.kinopoiskfintech.domain.models.Movie
import com.spinoza.moviesforfintech.data.network.model.CountryDto
import com.spinoza.moviesforfintech.data.network.model.GenreDto
import com.spinoza.moviesforfintech.data.network.model.MovieDto
import com.spinoza.moviesforfintech.data.network.model.MoviePreviewDto
import com.spinoza.moviesforfintech.data.network.model.MoviePreviewListDto
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


fun MoviePreviewListDto.toModel(): List<Movie> = films.map { moviePreviewDto ->
    moviePreviewDto.toModel()
}


fun MoviePreviewDto.toModel(): Movie = Movie(
    filmId = filmId,
    nameRu = nameRu,
    year = year,
    countries = countriesToString(countries),
    genres = genresToString(genres),
    posterUrlPreview = posterUrlPreview,
    posterUrl = posterUrl,
    description = String(),
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
