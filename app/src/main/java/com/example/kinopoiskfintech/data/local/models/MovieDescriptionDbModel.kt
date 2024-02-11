package com.example.kinopoiskfintech.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinopoiskfintech.data.local.DatabaseConstants.DESCRIPTION_TABLE

@Entity(tableName = DESCRIPTION_TABLE)
data class MovieDescriptionDbModel(
    @PrimaryKey
    val filmId: Int,
    val nameRu: String,
    val year: Int,
    val countries: String,
    val genres: String,
    val posterUrl: String,
    val description: String,
    val posterUrlPreview: String,
)