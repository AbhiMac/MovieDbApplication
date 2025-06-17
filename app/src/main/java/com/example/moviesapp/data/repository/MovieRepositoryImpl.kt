package com.example.moviesapp.data.repository

import com.example.moviesapp.data.local.MovieDao
import com.example.moviesapp.data.local.toDomain
import com.example.moviesapp.data.local.toEntity
import com.example.moviesapp.data.remote.TMDBApiService
import com.example.moviesapp.data.remote.toDomain
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.utils.Constants.API_KEY
import com.example.moviesapp.utils.ErrorHandler
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
class MovieRepositoryImpl @Inject constructor(
    private val api: TMDBApiService, private val dao: MovieDao
) : MovieRepository {

    private val apiKey = API_KEY
    override suspend fun getNowPlayingMovies(): List<Movie> {
        return try {
            api.getNowPlayingMovies(apiKey).results.map { it.toDomain() }
        } catch (e: Exception) {
            throw Exception(ErrorHandler.getErrorMessage(e))
        }
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return try {
            api.getPopularMovies(apiKey).results.map { it.toDomain() }
        } catch (e: Exception) {
            throw Exception(ErrorHandler.getErrorMessage(e))
        }
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return try {
            api.searchMovies(query, apiKey).results.map { it.toDomain() }
        } catch (e: Exception) {
            throw Exception(ErrorHandler.getErrorMessage(e))
        }
    }

    override suspend fun getMovieDetails(id: Int): Movie {
        return try {
            api.getMovieDetails(id, apiKey).toDomain()
        } catch (e: Exception) {
            throw Exception(ErrorHandler.getErrorMessage(e))
        }
    }

    override suspend fun bookmarkMovie(movie: Movie) {
        dao.insert(movie.toEntity())
    }

    override suspend fun removeBookmark(id: Int) {
        dao.deleteById(id)
    }

    override fun getBookmarks(): Flow<List<Movie>> {
        return try {
            dao.getAll().map { list -> list.map { it.toDomain() } }
        } catch (e: Exception) {
            throw Exception(ErrorHandler.getErrorMessage(e))
        }
    }
}
