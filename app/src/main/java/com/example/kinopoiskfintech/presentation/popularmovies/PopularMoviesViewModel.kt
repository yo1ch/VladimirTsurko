package com.example.kinopoiskfintech.presentation.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.domain.usecase.ChangeMovieFavouriteStatusUseCase
import com.example.kinopoiskfintech.domain.usecase.GetAllPopularMoviesUseCase
import com.example.kinopoiskfintech.domain.usecase.GetPopularMoviesByQueryUseCase
import com.example.kinopoiskfintech.domain.usecase.LoadPopularMoviesUseCase
import com.example.kinopoiskfintech.utils.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val loadPopularMoviesUseCase: LoadPopularMoviesUseCase,
    private val getAllPopularMoviesUseCase: GetAllPopularMoviesUseCase,
    private val changeMovieFavouriteStatusUseCase: ChangeMovieFavouriteStatusUseCase,
    private val getPopularMoviesByQueryUseCase: GetPopularMoviesByQueryUseCase,
) : ViewModel() {


    private val _movies: MutableStateFlow<ResourceState<List<Movie>>> =
        MutableStateFlow(ResourceState.Loading)
    val movies = _movies.asStateFlow()

    private val _moviesByQuery: MutableStateFlow<List<Movie>> =
        MutableStateFlow(emptyList())
    val moviesByQuery = _moviesByQuery.asStateFlow()

    init {
        getMovies()
        getAllPopularMoviesUseCase()
            .onStart {
                _movies.emit(ResourceState.Loading)
            }
            .onEach { movies ->
                _movies.emit(ResourceState.Content(content = movies))
            }.launchIn(viewModelScope)
    }

    fun getMoviesByQuery(query: String){
        if(query.isNotEmpty()){
            viewModelScope.launch {
                _moviesByQuery.value = getPopularMoviesByQueryUseCase(query)
            }
        }
    }
    fun changeMovieFavouriteStatus(movie: Movie){
        viewModelScope.launch {
            changeMovieFavouriteStatusUseCase(movie)
        }
    }
    fun getMovies() {
        viewModelScope.launch {
            try {
                loadPopularMoviesUseCase()
            }catch (e: Exception){

            }
        }
    }

}