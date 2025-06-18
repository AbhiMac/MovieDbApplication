package com.example.moviesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.moviesapp.domain.usecase.GetPopularMoviesUseCase
import com.example.moviesapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _nowPlayingState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val nowPlayingState: StateFlow<UiState<List<Movie>>> = _nowPlayingState

    private val _latestMoviesState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val latestMoviesState: StateFlow<UiState<List<Movie>>> = _latestMoviesState

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            launch {
                _nowPlayingState.value = UiState.Loading
                try {
                    val movies = getNowPlayingMoviesUseCase()
                    _nowPlayingState.value = UiState.Success(movies)
                } catch (e: Exception) {
                    _nowPlayingState.value = UiState.Error(e.message ?: "Failed to load Now Playing")
                }
            }

            launch {
                _latestMoviesState.value = UiState.Loading
                try {
                    val movies = getPopularMoviesUseCase()
                    _latestMoviesState.value = UiState.Success(movies)
                } catch (e: Exception) {
                    _latestMoviesState.value = UiState.Error(e.message ?: "Failed to load Latest Movies")
                }
            }
        }
    }


}