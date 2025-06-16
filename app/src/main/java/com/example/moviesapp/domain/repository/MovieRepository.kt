package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
interface MovieRepository {

    suspend fun getPopularMovies(): List<Movie>
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun bookmarkMovie(movie: Movie)
    suspend fun removeBookmark(id: Int)
    fun getBookmarks(): Flow<List<Movie>>
}