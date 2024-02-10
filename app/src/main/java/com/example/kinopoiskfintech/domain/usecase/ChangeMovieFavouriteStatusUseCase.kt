package com.example.kinopoiskfintech.domain.usecase

import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.domain.repository.MoviesRepository
import javax.inject.Inject

class ChangeMovieFavouriteStatusUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
){
    suspend operator fun invoke(movie: Movie) = moviesRepository.changeMovieFavouriteStatus(movie)
}