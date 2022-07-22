package com.example.movieapp.ui.actors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R

class ActorsActivity : AppCompatActivity() {

    private var actors: List<Actor> = emptyList()

    private fun getActors() {
        actors = listOf(
            Actor(0, "Leonardo DiCaprio", false),
            Actor(1, "Jack Dawson", false),
            Actor(2, "Kate Winslet", false),
            Actor(3, " Rose Dewitt Bukater", false),
            Actor(4, "Vin Diesel", false),
            Actor(5, "Paul Walker", false),
        )
        setupRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors)
        getActors()
    }

    private fun setupRecyclerView() {
        val rvActors = findViewById<RecyclerView>(R.id.rvActors)
        rvActors.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvActors.adapter = ActorsAdapter(actors)
    }
}
