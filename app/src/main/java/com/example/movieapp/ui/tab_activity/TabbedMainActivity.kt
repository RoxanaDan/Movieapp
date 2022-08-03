package com.example.movieapp.ui.tab_activity

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityTabbedMainBinding

class TabbedMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabbedMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTabbedMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        supportActionBar?.hide()
//        setContentView(R.layout.activity_main)

//        getSupportActionBar()?.hide();
//        setContentView(R.layout.activity_main);
    }
}