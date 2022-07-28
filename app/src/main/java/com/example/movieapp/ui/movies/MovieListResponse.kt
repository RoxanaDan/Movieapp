package com.example.movieapp.ui.movies

import com.google.gson.annotations.SerializedName

class MovieListResponse (
    @SerializedName("results")
    var movies: List<MovieResponse>
)