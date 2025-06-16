package com.example.moviesapp.domain.usecase

import com.example.moviesapp.domain.repository.MovieRepository
import jakarta.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.removeBookmark(id)
    }
}