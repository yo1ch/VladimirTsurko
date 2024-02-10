package com.example.kinopoiskfintech.data.remote

import com.spinoza.moviesforfintech.data.network.model.MovieDto
import com.spinoza.moviesforfintech.data.network.model.MoviePreviewListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopPopularMovies(
        @Query("page") page: Int
    ): Response<MoviePreviewListDto>
    @GET("api/v2.2/films/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int
    ): Response<MovieDto>

}