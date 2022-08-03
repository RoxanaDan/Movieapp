package com.example.movieapp.ui.movies

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.util.Constants.IMAGE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesAdapter(private val moviesList: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    private val movieRep: MovieRepository = MovieRepository.instance

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var favorite: Boolean = false
        var watched: Boolean = false

        val movieName: TextView = view.findViewById(R.id.txtMovieName)
        val parentView: ConstraintLayout = view.findViewById(R.id.parent)
        val textDescription: TextView = view.findViewById(R.id.txtMovieDescription)
        val starIcon: ImageView = view.findViewById(R.id.btnFavorite)
        val imgMovie: ImageView = view.findViewById(R.id.imgMovie)
        val ivVote: TextView = view.findViewById(R.id.tvVote)
        val iv_release_date: TextView = view.findViewById(R.id.iv_release_date)
        val watchedButton: ImageButton = view.findViewById(R.id.btnWatched)
        val favoriteButton: ImageButton = view.findViewById(R.id.btnFavorite)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.movieName.text = movie.title
        holder.textDescription.text = movie.overview
        holder.iv_release_date.text = movie.release_date
        holder.ivVote.text = movie.vote_average.toString()
        holder.favorite = movie.isFavorite
        holder.watched = movie.isWatched

        updateFavoriteButton(holder)
        updateWatchedButton(holder)

        Glide.with(holder.imgMovie.context).load(IMAGE_URL + movie.poster_path)
            .into(holder.imgMovie)
//        selectMovie(holder, movie)

        holder.parentView.setOnClickListener {
//            movie.isSelected = !movie.isSelected
//            selectMovie(holder, movie)
        }

        holder.favoriteButton.setOnClickListener() {
            holder.favorite = !holder.favorite
            moviesList[position].isFavorite = holder.favorite
            updateFavoriteButton(holder)
            updateDatabase(movie)
        }

        holder.watchedButton.setOnClickListener() {
            holder.watched = !holder.watched
            moviesList[position].isWatched = holder.watched
            updateWatchedButton(holder)
            updateDatabase(movie)
        }
    }

    private fun updateFavoriteButton(holder: ViewHolder) {
        holder.favoriteButton.background.colorFilter = PorterDuffColorFilter(
            when (holder.favorite) {
                true -> ContextCompat.getColor(holder.parentView.context, R.color.gold)
                else -> ContextCompat.getColor(holder.parentView.context, R.color.white_18a)
            }, PorterDuff.Mode.SRC
        )

        holder.favoriteButton.colorFilter = PorterDuffColorFilter(
            when (holder.favorite) {
                true -> ContextCompat.getColor(holder.parentView.context, R.color.black)
                else -> ContextCompat.getColor(holder.parentView.context, R.color.gold)
            }, PorterDuff.Mode.SRC_ATOP
        )
    }

    private fun updateWatchedButton(holder: ViewHolder) {
        holder.watchedButton.background.colorFilter = PorterDuffColorFilter(
            when (holder.watched) {
                true -> ContextCompat.getColor(holder.parentView.context, R.color.gold)
                else -> ContextCompat.getColor(holder.parentView.context, R.color.white_18a)
            }, PorterDuff.Mode.SRC
        )
        holder.watchedButton.colorFilter = PorterDuffColorFilter(
            when (holder.watched) {
                true -> ContextCompat.getColor(holder.parentView.context, R.color.black)
                else -> ContextCompat.getColor(holder.parentView.context, R.color.gold)
            }, PorterDuff.Mode.SRC_ATOP
        )
    }

    private fun filterWithFlags() = moviesList.filter { it.isFavorite || it.isWatched }

    private fun updateDatabase(movie: Movie){
        GlobalScope.launch(Dispatchers.IO) {
            val saved = ArrayList<Movie>(movieRep.getAllLocalMovies())
            val filtered = ArrayList<Movie>(filterWithFlags())

            saved.remove(movie)
            movieRep.replaceAllLocal(saved.union(filtered).toList())
        }
    }

    override fun getItemCount() = moviesList.size
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

