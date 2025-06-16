package com.example.moviesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.moviesapp.data.local.AppDatabase
import com.example.moviesapp.data.local.MovieDao
import com.example.moviesapp.data.remote.TMDBApiService
import com.example.moviesapp.data.repository.MovieRepositoryImpl
import com.example.moviesapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTMDBApi(): TMDBApiService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "movie_db").build()

    @Provides
    @Singleton
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: TMDBApiService,
        dao: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(api, dao)
    }
}
