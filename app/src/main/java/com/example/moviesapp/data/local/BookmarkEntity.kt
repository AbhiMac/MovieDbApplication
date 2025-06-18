package com.example.moviesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesapp.domain.model.Movie

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
)

fun BookmarkEntity.toDomain(): Movie = Movie(id, title, posterPath, overview)
fun Movie.toBookmarkEntity(): BookmarkEntity = BookmarkEntity(id, title, posterPath, overview)
