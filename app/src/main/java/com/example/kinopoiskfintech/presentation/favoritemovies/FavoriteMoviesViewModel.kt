package com.example.kinopoiskfintech.presentation.favoritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.domain.usecase.ChangeMovieFavouriteStatusUseCase
import com.example.kinopoiskfintech.domain.usecase.GetFavouriteMoviesByQueryUseCase
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
    private val getFavouriteMoviesByQueryUseCase: GetFavouriteMoviesByQueryUseCase,
) : ViewModel() {

    private val _movies: MutableStateFlow<ResourceState<List<Movie>>> = MutableStateFlow(ResourceState.Loading)
    val movies = _movies.asStateFlow()

    private val _moviesByQuery: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val moviesByQuery = _moviesByQuery.asStateFlow()

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

    fun getFavouriteByQuery(query: String){
        if(query.isNotEmpty()){
            viewModelScope.launch {
                _moviesByQuery.value = getFavouriteMoviesByQueryUseCase(query)
            }
        }
    }

    fun changeMovieFavouriteStatus(movie: Movie) {
        viewModelScope.launch {
            changeMovieFavouriteStatusUseCase(movie)
        }
    }

}