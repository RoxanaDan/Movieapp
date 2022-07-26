package com.example.movieapp.ui.actors

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActorDAO {
    @Query("SELECT * from actors")
    fun getAll(): List<Actor>

    @Insert
    fun save(actor: Actor)

    @Insert
    fun saveAll(actors: List<Actor>)

    @Delete
    fun delete(actor: Actor)

    @Delete
    fun deleteAll(actors: List<Actor>)

    @Query("DELETE from actors")
    fun deleteAll()

    fun replaceAll(actors: List<Actor>) {
        deleteAll()
        saveAll(actors)
    }

    @Query("SELECT COUNT (id) from actors")
    fun getCount(): Int
}