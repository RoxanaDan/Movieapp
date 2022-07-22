package com.example.movieapp.ui.genres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenresActivity : AppCompatActivity() {

    private var genres: List<Genre> = emptyList()
    private val genreRepository = GenreRepository.instance

    private fun getGenres() {
        GlobalScope.launch(Dispatchers.IO){
            genres = genreRepository.getAllRemoteGenres()
            withContext(Dispatchers.Main){
                setupRecyclerView()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)
        getGenres()
    }

    private fun setupRecyclerView() {
        val rvGenres = findViewById<RecyclerView>(R.id.rvGenres)
        rvGenres.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvGenres.adapter = GenresAdapter(genres)
    }
}

// =