import androidx.room.ColumnInfo
import com.example.movieapp.ui.movies.Movie
import com.example.movieapp.ui.movies.MovieResponse

class MovieMapper {

    fun map(movieResponse: MovieResponse): Movie {
        return Movie(
            id = movieResponse.id,
            poster_path = movieResponse.poster_path,
            adult = movieResponse.adult.toBoolean(),
            overview = movieResponse.overview,
            release_date = movieResponse.release_date,
            title = movieResponse.title,
            original_language = movieResponse.original_language,
            vote_count = movieResponse.vote_count,
            video = movieResponse.video,
            vote_average = movieResponse.vote_average,
            isSelected = false,
            isFavorite = false,
            isWatched = false
        )
    }
}
