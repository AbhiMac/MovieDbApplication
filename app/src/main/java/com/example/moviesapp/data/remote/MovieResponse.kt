package com.example.moviesapp.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Created by Abhijeet Sharma on 6/16/2025
 */
data class MovieResponse(@SerializedName("results") val results: List<MovieDto>)