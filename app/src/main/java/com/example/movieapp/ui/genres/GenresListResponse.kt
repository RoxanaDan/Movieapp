package com.example.movieapp.ui.genres

import com.example.movieapp.ui.genres.GenreResponse
import com.google.gson.annotations.SerializedName

class GenresListResponse (
    @SerializedName("genres")
    var genres: List<GenreResponse>
)