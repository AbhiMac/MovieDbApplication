package com.example.moviesapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
interface TMDBApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): MovieResponse
}