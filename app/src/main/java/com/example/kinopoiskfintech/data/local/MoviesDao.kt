package com.example.kinopoiskfintech.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinopoiskfintech.data.local.DatabaseConstants.DESCRIPTION_TABLE
import com.example.kinopoiskfintech.data.local.DatabaseConstants.FAVOURITE_TABLE
import com.example.kinopoiskfintech.data.local.models.MovieDbModel
import com.example.kinopoiskfintech.data.local.models.MovieDescriptionDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM $FAVOURITE_TABLE WHERE isFavorite=1")
    fun getAllFavouriteFilms(): Flow<List<MovieDbModel>>
    @Query("SELECT * FROM $FAVOURITE_TABLE")
    fun getAllPopularFilms(): Flow<List<MovieDbModel>>
    @Query("SELECT * FROM $FAVOURITE_TABLE WHERE filmId=:filmId")
    suspend fun getMovieById(filmId: Int): MovieDbModel

    @Query("SELECT * FROM $DESCRIPTION_TABLE WHERE filmId=:filmId")
    suspend fun getDescriptionById(filmId: Int): List<MovieDescriptionDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDescription(film: MovieDescriptionDbModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilmToFavourite(film: MovieDbModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilmsToFavourite(filmsList: List<MovieDbModel>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun removeFilmFromFavourite(film: MovieDbModel)
    @Query("SELECT EXISTS (SELECT * FROM $FAVOURITE_TABLE WHERE filmId=:filmId)")
    suspend fun isFilmFavourite(filmId: Int): Boolean

    @Query("SELECT * FROM $FAVOURITE_TABLE WHERE nameRu LIKE '%' || :query || '%' AND isFavorite=1")
    suspend fun getFavouriteFilmsByQuery(query: String): List<MovieDbModel>

    @Query("SELECT * FROM $FAVOURITE_TABLE WHERE nameRu LIKE '%' || :query || '%'")
    suspend fun getPopularFilmsByQuery(query: String): List<MovieDbModel>

}