package com.example.kinopoiskfintech.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinopoiskfintech.data.local.DatabaseConstants.FAVOURITE_TABLE

@Entity(tableName = FAVOURITE_TABLE)
data class MovieDbModel(
    @PrimaryKey
    val filmId: Int,
    val nameRu: String,
    val year: Int,
    val countries: String,
    val genres: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val description: String,
)