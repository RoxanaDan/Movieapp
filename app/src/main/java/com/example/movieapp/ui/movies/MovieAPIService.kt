package com.example.movieapp.ui.movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Call<MovieListResponse>

    @GET("search/movie")
    fun getSearchedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Call<MovieListResponse>
}