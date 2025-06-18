package com.example.moviesapp.data.remote

import com.example.moviesapp.domain.model.Movie
import com.google.gson.annotations.SerializedName

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("overview") val overview: String
)

fun MovieDto.toDomain(isNowPlaying: Boolean = false,
                      isPopular: Boolean = false): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath ?: "",
        overview = overview,
        isNowPlaying = isNowPlaying,
        isPopular = isPopular
    )
}