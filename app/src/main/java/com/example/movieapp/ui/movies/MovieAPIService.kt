package com.example.movieapp.ui.movies

import com.example.movieapp.ui.genres.GenresListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String


    ): Call<MovieListResponse>
}