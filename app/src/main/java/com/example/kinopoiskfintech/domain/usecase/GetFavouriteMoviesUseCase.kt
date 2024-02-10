package com.example.kinopoiskfintech.domain.usecase

import com.example.kinopoiskfintech.domain.repository.MoviesRepository
import javax.inject.Inject

class GetFavouriteMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
){
    operator fun invoke() = moviesRepository.getFavouriteMovies()

}