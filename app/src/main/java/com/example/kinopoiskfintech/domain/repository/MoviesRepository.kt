package com.example.kinopoiskfintech.domain.repository

import com.example.kinopoiskfintech.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun loadPopularMovies()
    fun getMovies(): Flow<List<Movie>>
    suspend fun getMovieById(id: Int): Result<Movie>

    suspend fun changeMovieFavouriteStatus(movie: Movie)

    fun getFavouriteMovies(): Flow<List<Movie>>

}