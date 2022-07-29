package com.example.movieapp.ui.movies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDAO {

    @Query("SELECT * from movies")
    fun getAll(): List<Movie>

    @Insert
    fun save(movie: Movie)

    @Insert
    fun saveAll(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)

    @Delete
    fun deleteAll(movies: List<Movie>)

    @Query("DELETE from movies")
    fun deleteAll()

    fun replaceAll(movies: List<Movie>) {
        deleteAll()
        saveAll(movies)
    }

    @Query("SELECT COUNT (id) from movies")
    fun getCount(): Int

}