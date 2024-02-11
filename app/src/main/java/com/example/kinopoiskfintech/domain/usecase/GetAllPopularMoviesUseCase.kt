package com.example.kinopoiskfintech.domain.usecase

import com.example.kinopoiskfintech.domain.repository.MoviesRepository
import javax.inject.Inject

class GetAllPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke() = repository.getMovies()
}