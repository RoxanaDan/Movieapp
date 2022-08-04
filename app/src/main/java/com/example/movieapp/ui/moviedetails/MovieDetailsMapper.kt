package com.example.movieapp.ui.moviedetails

import com.example.movieapp.ui.movies.Movie
import com.example.movieapp.ui.movies.MovieResponse

class MovieDetailsMapper {
    fun map(movieResponse: MovieDetailsResponse): MovieDetails {
        return MovieDetails(
            id = movieResponse.id,
            adult = movieResponse.adult.toBoolean(),
            overview = movieResponse.overview.toString(),  ///??
            posterPath = movieResponse.poster_path,
            release_date = movieResponse.release_date,
            title = movieResponse.title,
            original_language = movieResponse.original_language,
            vote_count = movieResponse.vote_count,
            video = movieResponse.video,
            vote_average = movieResponse.vote_average,
            genres = movieResponse.genres,
            original_title = movieResponse.title,
            popularity = movieResponse.popularity,
            status = movieResponse.status
        )
    }
}
