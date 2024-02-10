package com.example.kinopoiskfintech.domain.repository

import com.example.kinopoiskfintech.domain.models.Movie

interface MoviesRepository {

    suspend fun getPopularMovies(): Result<List<Movie>>

    suspend fun getMovieById(id: Int): Result<Movie>

}