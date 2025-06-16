package com.example.moviesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesapp.domain.model.Movie

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@Entity(tableName = "bookmarks")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
)

fun MovieEntity.toDomain(): Movie = Movie(id, title, posterPath, overview)
fun Movie.toEntity(): MovieEntity = MovieEntity(id, title, posterPath, overview)
