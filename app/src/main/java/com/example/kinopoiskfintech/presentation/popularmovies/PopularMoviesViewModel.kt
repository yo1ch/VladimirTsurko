package com.example.kinopoiskfintech.presentation.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.domain.usecase.ChangeMovieFavouriteStatusUseCase
import com.example.kinopoiskfintech.domain.usecase.GetPopularMoviesUseCase
import com.example.kinopoiskfintech.utils.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val changeMovieFavouriteStatusUseCase: ChangeMovieFavouriteStatusUseCase,
) : ViewModel() {

    private val _movies: MutableStateFlow<ResourceState<List<Movie>>> =
        MutableStateFlow(ResourceState.Loading)
    val movies = _movies.asStateFlow()

    init {
        getMovies()
    }

    fun changeMovieFavouriteStatus(movie: Movie){
        viewModelScope.launch {
            changeMovieFavouriteStatusUseCase(movie)
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .onSuccess { movies ->
                    _movies.emit(ResourceState.Content(content = movies))
                }.onFailure {

                }
        }
    }

}