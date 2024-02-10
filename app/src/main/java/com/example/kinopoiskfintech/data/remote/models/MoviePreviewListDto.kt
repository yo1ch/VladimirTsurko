package com.spinoza.moviesforfintech.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviePreviewListDto(
    @SerializedName("pagesCount")
    val pagesCount: Int,
    @SerializedName("films")
    val films: List<MoviePreviewDto>,
)