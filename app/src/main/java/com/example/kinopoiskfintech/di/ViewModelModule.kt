package com.example.kinopoiskfintech.di

import androidx.lifecycle.ViewModel
import com.example.kinopoiskfintech.presentation.moviedetails.MovieDetailsViewModel
import com.example.kinopoiskfintech.presentation.popularmovies.PopularMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    @Binds
    fun bindMovieDetailsViewModel(impl: MovieDetailsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PopularMoviesViewModel::class)
    @Binds
    fun bindPopularMoviesViewModel(impl: PopularMoviesViewModel): ViewModel
}