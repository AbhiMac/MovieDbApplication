package com.example.moviesapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.usecase.GetMovieDetailsUseCase
import com.example.moviesapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movieState = MutableStateFlow< UiState<Movie?>>(UiState.Loading)
    val movie: StateFlow<UiState<Movie?>> = _movieState

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieState.value = UiState.Loading
            try {
                val movie = getMovieDetailsUseCase(movieId)
                _movieState.value = UiState.Success(movie)
            } catch (e: Exception) {
                _movieState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
