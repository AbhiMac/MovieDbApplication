package com.example.moviesapp.domain.usecase

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieRepository
import jakarta.inject.Inject

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> = repository.getPopularMovies()
}