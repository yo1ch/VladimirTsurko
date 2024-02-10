package com.example.kinopoiskfintech.data.remote.repository

import com.example.kinopoiskfintech.data.remote.MoviesApi
import com.example.kinopoiskfintech.data.remote.mappers.toModel
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.domain.repository.MoviesRepository
import com.example.kinopoiskfintech.utils.toResult
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
): MoviesRepository {
    override suspend fun loadAllFilms(): List<Movie> = emptyList()

    override suspend fun getMovieById(id: Int): Result<Movie> {
        val response = moviesApi.getMovieById(id)
        return response.toResult().map { movieResponse ->
            movieResponse.toModel()
        }
    }
}