package com.example.kinopoiskfintech.presentation.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.domain.usecase.GetMovieByIdUseCase
import com.example.kinopoiskfintech.utils.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
): ViewModel() {

    private val _movie: MutableStateFlow<ResourceState<Movie>> = MutableStateFlow(ResourceState.Loading)
    val movie = _movie.asStateFlow()

    fun getMovieById(id: Int){
        viewModelScope.launch {
            getMovieByIdUseCase(
                id = id
            ).onSuccess {
                _movie.emit(ResourceState.Content(it))
            }.onFailure {
                _movie.emit(ResourceState.Error())
            }
        }
    }

}