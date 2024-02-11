package com.example.kinopoiskfintech.data.remote.repository

import com.example.kinopoiskfintech.data.local.MoviesDao
import com.example.kinopoiskfintech.data.mappers.toDbModel
import com.example.kinopoiskfintech.data.mappers.toDescriptionDnModel
import com.example.kinopoiskfintech.data.remote.MoviesApi
import com.example.kinopoiskfintech.data.mappers.toModel
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.domain.repository.MoviesRepository
import com.example.kinopoiskfintech.utils.toResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
) : MoviesRepository {

    private var currentPage = FIRST_PAGE

    override suspend fun getMovieById(id: Int): Result<Movie> {
        val movies = moviesDao.getDescriptionById(id)
        return if (movies.isEmpty()) {
            val response = moviesApi.getMovieById(id)
            response.toResult().map { movieResponse ->
                moviesDao.insertMovieDescription(movieResponse.toDescriptionDnModel())
                movieResponse.toModel()
            }
        } else Result.success(movies.first().toModel())


    }

    override suspend fun changeMovieFavouriteStatus(movie: Movie) {
        when (movie.isFavourite) {
            true -> moviesDao.removeFilmFromFavourite(
                movie.toDbModel().copy(isFavorite = false)
            )
            false -> moviesDao.insertFilmToFavourite(
                movie.toDbModel().copy(isFavorite = true)
            )
        }
    }

    override fun getFavouriteMovies(): Flow<List<Movie>> =
        moviesDao.getAllFavouriteFilms().map { dbModels ->
            dbModels.map { it.toModel() }
        }

    override fun getMovies(): Flow<List<Movie>> =
        moviesDao.getAllPopularFilms().map { dbModels ->
            dbModels.map { it.toModel() }
        }

    override suspend fun loadPopularMovies(){
        val page = if (currentPage < LAST_PAGE) currentPage++ else currentPage
        val response = moviesApi.getTopPopularMovies(page)
        if (response.isSuccessful) {
            response.body()?.films
            ?.map {
                it.toDbModel()
            }
            ?.let {
                moviesDao.insertFilmsToFavourite(it)
            }
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val LAST_PAGE = 5
    }
}