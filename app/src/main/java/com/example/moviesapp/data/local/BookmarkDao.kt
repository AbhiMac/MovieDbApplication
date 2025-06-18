package com.example.moviesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@Dao
interface BookmarkDao {

    @Query("DELETE FROM bookmark")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: BookmarkEntity)

    @Query("DELETE FROM bookmark WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM movies")
    fun getAll(): Flow<List<BookmarkEntity>>
}