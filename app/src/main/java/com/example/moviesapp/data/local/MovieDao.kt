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
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM movies")
     fun getAll(): Flow<List<MovieEntity>>
}