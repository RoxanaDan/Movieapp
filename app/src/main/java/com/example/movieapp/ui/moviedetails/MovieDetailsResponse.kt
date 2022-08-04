package com.example.movieapp.ui.moviedetails

import com.example.movieapp.ui.genres.Genre
import com.google.gson.annotations.SerializedName

class MovieDetailsResponse (
    var id : Int,
    @SerializedName("poster_path")
    var poster_path: String?,
    @SerializedName("adult")
    var adult: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("release_date")
    var release_date: String?,
    @SerializedName("title")
    var title: String,
    @SerializedName("original_language")
    var original_language: String?,
    @SerializedName("vote_count")
    var vote_count: Int?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var vote_average: Double,
    @SerializedName("genres")
    var genres: List<Genre>,
    @SerializedName("popularity")
    var popularity: Number?,
    @SerializedName("status")
    var status: String,
    )
