package com.example.movieapp.ui.moviedetails

import com.example.movieapp.ui.genres.Genre

data class MovieDetails(
    var id: Int,
    var adult: Boolean,
    var original_language: String?,
    var video: Boolean?,
    var genres: List<Genre>,
    var original_title: String,
    var overview: String,
    var popularity: Number?,
    var posterPath: String?,
    var release_date: String?,
    var status: String,
    var title: String,
    var vote_average: Number?,
    var vote_count: Int?
)