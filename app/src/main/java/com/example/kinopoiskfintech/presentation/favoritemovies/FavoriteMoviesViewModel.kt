package com.example.kinopoiskfintech.presentation.favoritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.domain.usecase.ChangeMovieFavouriteStatusUseCase
import com.example.kinopoiskfintech.domain.usecase.GetFavouriteMoviesUseCase
import com.example.kinopoiskfintech.utils.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMoviesViewModel @Inject constructor(
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
    private val changeMovieFavouriteStatusUseCase: ChangeMovieFavouriteStatusUseCase,
) : ViewModel() {

    private val _movies: MutableStateFlow<ResourceState<List<Movie>>> = MutableStateFlow(ResourceState.Loading)
    val movies = _movies.asStateFlow()

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        getFavouriteMoviesUseCase()
            .onStart {
                _movies.emit(ResourceState.Loading)
            }
            .onEach { movies ->
                _movies.emit(ResourceState.Content(content = movies))
            }.launchIn(viewModelScope)
    }

    fun changeMovieFavouriteStatus(movie: Movie) {
        viewModelScope.launch {
            changeMovieFavouriteStatusUseCase(movie)
        }
    }

}