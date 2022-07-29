package com.example.movieapp.ui.genres

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenreDAO {

    @Query("SELECT * from genres")
    fun getAll(): List<Genre>

    @Query("SELECT id FROM genres")
    fun getAllIds(): List<Int>

    @Insert
    fun save(genre: Genre)

    @Insert
    fun saveAll(genres: List<Genre>)

    @Delete
    fun delete(genre: Genre)

    @Delete
    fun deleteAll(genres: List<Genre>)

    @Query("DELETE from genres")
    fun deleteAll()

    fun replaceAll(genres: List<Genre>) {
        deleteAll()
        saveAll(genres)
    }

    @Query("SELECT COUNT (id) from genres")
    fun getCount(): Int

}