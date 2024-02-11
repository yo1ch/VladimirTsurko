package com.example.kinopoiskfintech.domain.usecase

import com.example.kinopoiskfintech.domain.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesByQueryUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(query: String) = repository.getPopularMoviesByQuery(query)
}