package com.example.movieapp.ui.movies

import com.google.gson.annotations.SerializedName

class MoviesListResponse (
    @SerializedName("Movies")
    var movie: List<MovieResponse>
)