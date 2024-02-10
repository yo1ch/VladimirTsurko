package com.example.kinopoiskfintech.di

import com.example.kinopoiskfintech.data.remote.repository.MoviesRepositoryImpl
import com.example.kinopoiskfintech.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @AppScope
    @Binds
    fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}