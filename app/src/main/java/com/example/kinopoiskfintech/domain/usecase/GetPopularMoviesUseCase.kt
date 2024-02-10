package com.example.kinopoiskfintech.domain.usecase

import com.example.kinopoiskfintech.domain.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke() = moviesRepository.getPopularMovies()

}