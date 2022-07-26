package com.example.movieapp.ui.movies
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")

data class Movie(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "poster_path")
    var poster_path: String?,
    @ColumnInfo(name = "adult")
    var adult: Boolean?,
    @ColumnInfo(name = "overview")
    var overview: String?,
    @ColumnInfo(name = "release_date")
    var release_date: String?,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "original_language")
    var original_language: String?,
    @ColumnInfo(name = "vote_count")
    var vote_count: Int?,
    @ColumnInfo(name = "video")
    var video: Boolean?,
    @ColumnInfo(name = "vote_average")
    var vote_average: Double?,
    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean,
    @ColumnInfo(name = "is_watched")
    var isWatched: Boolean)

{
    override fun equals(other: Any?) = (other is Movie) && id == other.id
}
//override fun toString(): String {
//    return "Movie(id=$id, name='$poster_path', name ='$adult'name='$original_language', name =$vote_count, isSelected='$isSelected')"
//}

//    @ColumnInfo(name = "page")
//    var page: Int,
//    @ColumnInfo(name = "results")
//    var results: List<Int>,
//    @ColumnInfo(name = "genre_ids")
//    var genre_ids: List<Int>,
//   @PrimaryKey
//   @NonNull
//    @ColumnInfo(name = "id")
//    var id: Int,
//    @ColumnInfo(name = "original_title")
//    var original_title: String,
//    @ColumnInfo(name = "backdrop_path")
//    var backdrop_path: String
//    @ColumnInfo(name = "total_results")
//    val total_results: Int,
//    @ColumnInfo(name = "total_pages")
//    val total_pages: Int





