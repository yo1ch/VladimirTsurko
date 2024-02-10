package com.example.kinopoiskfintech.di

import android.app.Application
import com.example.kinopoiskfintech.presentation.favoritemovies.FavoriteMoviesFragment
import com.example.kinopoiskfintech.presentation.moviedetails.MovieDetailsFragment
import com.example.kinopoiskfintech.presentation.popularmovies.PopularMoviesFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(fragment: PopularMoviesFragment)
    fun inject(fragment: FavoriteMoviesFragment)
    fun inject(fragment: MovieDetailsFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}