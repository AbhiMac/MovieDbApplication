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
//                    delay(500) // simulate loading
//                    _nowPlayingState.value = UiState.Success(getStaticNowPlaying())
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
    private fun getStaticNowPlaying(): List<Movie> {
        return listOf(
            Movie(id = 1, title = "Inception", posterPath = "/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg", overview = "Mind-bending thriller."),
            Movie(id = 2, title = "Interstellar", posterPath = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", overview = "Beyond time and space."),
            Movie(id = 3, title = "The Dark Knight", posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg", overview = "The legend of Gotham."),
            Movie(id = 4, title = "Avatar", posterPath = "/6EiRUJpuoeQPghrs3YNktfnqOVh.jpg", overview = "Epic alien world."),
            Movie(id = 5, title = "Avengers: Endgame", posterPath = "/or06FN3Dka5tukK1e9sl16pB3iy.jpg", overview = "End of an era.")
        )
    }

}