package com.example.moviesapp.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.usecase.BookmarkMovieUseCase
import com.example.moviesapp.domain.usecase.GetBookmarksUseCase
import com.example.moviesapp.domain.usecase.RemoveBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
/**
 * Created by Abhijeet Sharma on 6/16/2025
 */

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : ViewModel() {

    val bookmarks: StateFlow<List<Movie>> = getBookmarksUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun remove(id: Int) {
        viewModelScope.launch {
            removeBookmarkUseCase(id)
        }
    }

}