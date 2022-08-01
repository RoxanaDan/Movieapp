package com.example.movieapp.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.util.Constants
import com.example.movieapp.util.Constants.IMAGE_URL

class MoviesAdapter(private val moviesList: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieName: TextView = view.findViewById(R.id.txtMovieName)
        val parentView: ConstraintLayout = view.findViewById(R.id.parent)
        val textDescription: TextView = view.findViewById(R.id.txtMovieDescription)
        val starIcon: ImageView = view.findViewById(R.id.star)
        val imgMovie: ImageView = view.findViewById(R.id.imgMovie)
        val watchedButton: Button = view.findViewById(R.id.btnWatched)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.movieName.text = movie.title
        holder.textDescription.text = movie.overview

        Glide.with(holder.imgMovie.context).load(IMAGE_URL + movie.poster_path).into(holder.imgMovie)
//        selectMovie(holder, movie)

        holder.parentView.setOnClickListener {
//            movie.isSelected = !movie.isSelected
//            selectMovie(holder, movie)

        }
    }

//    private fun selectMovie(holder: ViewHolder, movie: Movie) {
//        holder.parentView.setBackgroundColor(
//            when (movie.isSelected) {
//                true -> ContextCompat.getColor(holder.parentView.context, R.color.gold)
//                else -> ContextCompat.getColor(holder.parentView.context, R.color.black_transparent)
//            }
//        )
//        holder.starIcon.visibility = when (movie.isSelected) {
//            true -> View.VISIBLE
//            else -> View.INVISIBLE
//        }
//    }

    override fun getItemCount() = moviesList.size
}