package com.example.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.example.moviesapp.data.local.AppDatabase
import com.example.moviesapp.data.local.BookmarkDao
import com.example.moviesapp.data.local.MovieDao
import com.example.moviesapp.data.remote.TMDBApiService
import com.example.moviesapp.data.repository.MovieRepositoryImpl
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.utils.Constants.BASE_URL
import com.example.moviesapp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Use BODY for full logs
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideTMDBApi(okHttpClient: OkHttpClient): TMDBApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

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
