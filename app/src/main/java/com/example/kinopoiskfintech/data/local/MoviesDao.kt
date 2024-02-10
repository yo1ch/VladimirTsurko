package com.example.kinopoiskfintech.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinopoiskfintech.data.local.DatabaseConstants.FAVOURITE_TABLE
import com.example.kinopoiskfintech.data.local.models.MovieDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM $FAVOURITE_TABLE")
    fun getAllFavouriteFilms(): Flow<List<MovieDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilmToFavourite(film: MovieDbModel)

    @Query("DELETE FROM $FAVOURITE_TABLE WHERE filmId=:filmId")
    suspend fun removeFilmFromFavourite(filmId: Int)

    @Query("SELECT EXISTS (SELECT * FROM $FAVOURITE_TABLE WHERE filmId=:filmId)")
    suspend fun isFilmFavourite(filmId: Int): Boolean
}