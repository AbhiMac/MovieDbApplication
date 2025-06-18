package com.example.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

   // abstract fun bookmarkDao(): BookmarkDao
}