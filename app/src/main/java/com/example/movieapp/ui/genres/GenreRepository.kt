package com.example.movieapp.ui.genres

import com.example.movieapp.network.APIClient

class GenreRepository private constructor(){
    companion object{
        val instance = GenreRepository()
    }

    private val genreRemoteDataSource = GenreRemoteDataSource(APIClient.instance.retrofit)

    fun getAllRemoteGenres() = genreRemoteDataSource.getGenres()

}