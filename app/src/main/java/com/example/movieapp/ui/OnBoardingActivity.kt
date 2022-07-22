package com.example.movieapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.movieapp.R
import com.example.movieapp.ui.actors.ActorsActivity
import com.example.movieapp.ui.genres.GenresActivity

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setClickListeners()

    }

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, OnBoardingActivity::class.java))
        }
    }

    private fun setClickListeners() {
        val btnGenres = findViewById<Button>(R.id.btnGenres)
        btnGenres.setOnClickListener() {
            startActivity(Intent(this, GenresActivity::class.java))
        }
        val btnActors = findViewById<Button>(R.id.btnActors)
        btnActors.setOnClickListener() {
            startActivity(Intent(this, ActorsActivity::class.java))
        }
    }
}