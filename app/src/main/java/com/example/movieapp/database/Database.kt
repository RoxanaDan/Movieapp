package com.example.movieapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.ui.actors.Actor
import com.example.movieapp.ui.actors.ActorDAO
import com.example.movieapp.ui.genres.Genre
import com.example.movieapp.ui.genres.GenreDAO

class Database private constructor() {
    companion object {
        val instance = Database()
    }

    @androidx.room.Database(entities = [Genre::class, Actor::class], version = 3)

    abstract class MovieAppDatabase : RoomDatabase() {
        abstract fun genresDao(): GenreDAO
        abstract fun actorsDao(): ActorDAO
    }

    lateinit var movieAppDatabase: MovieAppDatabase
        private set

    fun initialise(context: Context) {
        this.movieAppDatabase = Room.databaseBuilder(
            context,
            MovieAppDatabase::class.java,
            "movie_app.db"
        ).fallbackToDestructiveMigration().build()
    }
}
