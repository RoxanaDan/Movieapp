package com.example.movieapp.ui.actors

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.ui.OnBoardingActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActorsActivity : AppCompatActivity() {

    private var actors: List<Actor> = emptyList()
    private val actorRepository = ActorRepository.instance

    private fun getActors() {
        GlobalScope.launch(Dispatchers.IO) {
            actors = actorRepository.getAllRemoteActors()
            withContext(Dispatchers.Main) {
                preselectedSavedActors()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors)

        setOnClickListeners()
        getActors()
    }

    private fun setOnClickListeners() {
        val btnSave: FloatingActionButton = findViewById(R.id.btnSave)
        btnSave.setOnClickListener() {
            saveActors()
        }
    }

    private fun getSelectedActors(): List<Actor> {
        return actors.filter { actor -> actor.isSelected }
    }

    private fun saveActors() {
        GlobalScope.launch(Dispatchers.IO) {
            actorRepository.deleteAllLocal()
            actorRepository.saveAllLocal(getSelectedActors())
        }
        OnBoardingActivity.open(this)
    }

    private fun setupRecyclerView() {
        val rvActors = findViewById<RecyclerView>(R.id.rvActors)
        rvActors.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvActors.adapter = ActorsAdapter(actors)
    }

    private fun preselectedSavedActors() {
        GlobalScope.launch(Dispatchers.IO) {
            val savedActors: List<Actor> = actorRepository.getAllLocalActors()
            withContext(Dispatchers.Main) {
                actors.forEach { actor -> actor.isSelected = savedActors.contains(actor) }
                setupRecyclerView()
            }
        }
    }
}

