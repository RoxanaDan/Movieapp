package com.example.movieapp.ui.genres

import com.example.movieapp.network.executeAndDeliver
import com.example.movieapp.util.Constants.API_KEY
import com.example.movieapp.util.Constants.LANGUAGE
import retrofit2.Retrofit

class GenreRemoteDataSource(retrofit: Retrofit) {
    private val apiService: GenresAPIService = retrofit.create(GenresAPIService::class.java)

    private var genreMapper = GenreMapper()

    fun getGenres(): List<Genre> {
        return apiService.getGenres(API_KEY, LANGUAGE)
            .executeAndDeliver()
            .genres
            .map { genreMapper.map(it) }
    }
}