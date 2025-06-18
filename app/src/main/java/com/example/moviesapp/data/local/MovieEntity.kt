package com.example.moviesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesapp.domain.model.Movie

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val isNowPlaying: Boolean,
    val isPopular: Boolean
)

fun MovieEntity.toDomain(): Movie = Movie(id, title, posterPath, overview,isNowPlaying,isPopular)
fun Movie.toEntity(): MovieEntity = MovieEntity(id, title, posterPath, overview,isNowPlaying,isPopular)
