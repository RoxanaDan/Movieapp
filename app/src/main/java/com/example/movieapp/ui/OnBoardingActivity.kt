package com.example.movieapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.movieapp.R
import com.example.movieapp.SearchActivity
import com.example.movieapp.ui.actors.ActorRepository
import com.example.movieapp.ui.actors.ActorsActivity
import com.example.movieapp.ui.genres.GenreRepository
import com.example.movieapp.ui.genres.GenresActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OnBoardingActivity : AppCompatActivity() {

    private val genreRepository = GenreRepository.instance
    private val actorRepository = ActorRepository.instance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        setClickListeners()
    }

    private fun setClickListeners() {
        val genresButton = findViewById<Button>(R.id.btnGenres)
        genresButton.setOnClickListener {
            startActivity(Intent(this, GenresActivity::class.java))
        }
        val actorsButton = findViewById<Button>(R.id.btnActors)
        actorsButton.setOnClickListener {
            startActivity(Intent(this, ActorsActivity::class.java))
        }
    }

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, OnBoardingActivity::class.java))
        }
    }

    private fun verifyIfFilterIsSelected() {
        GlobalScope.launch(Dispatchers.IO) {
            val genreCount = genreRepository.getCount()
            val actorCount = actorRepository.getCount()
            withContext(Dispatchers.Main) {
                // operatiuni
                if (genreCount > 0 && actorCount > 0){
                    SearchActivity.open(this@OnBoardingActivity)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        verifyIfFilterIsSelected()
    }
}