package com.example.movieapp.ui.movies

import MovieMapper
import com.example.movieapp.network.executeAndDeliver
import com.example.movieapp.util.Constants.API_KEY
import com.example.movieapp.util.Constants.LANGUAGE
import retrofit2.Retrofit

class MovieRemoteDataSource(retrofit: Retrofit) {
    private val apiService: MovieAPIService = retrofit.create(MovieAPIService::class.java)

    private var movieMapper = MovieMapper()

    fun getMovies(): List<Movie> {
        return apiService.getMovies(API_KEY, LANGUAGE)
            .executeAndDeliver()
            .movies
            .map { movieMapper.map(it) }
    }
}