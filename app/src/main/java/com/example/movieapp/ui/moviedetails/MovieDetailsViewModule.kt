package com.example.movieapp.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.network.APIClient
import com.example.movieapp.ui.movies.MovieRemoteDataSource

class MovieDetailsViewModule : ViewModel(){
    val currentMovieId = MutableLiveData<Int>()
    private val movieRemoteDataSource = MovieRemoteDataSource(APIClient.instance.retrofit)
    var movie: MovieDetails? = null

    fun getMovieDetails() : MovieDetails? {
       return currentMovieId.value?.let { movieRemoteDataSource.getMovieDetails(it) }
    }
}