package com.example.movieapp.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.ui.movies.Movie
import com.example.movieapp.ui.movies.MovieRepository
import com.example.movieapp.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WatchedMoviesAdapter(
    private val moviesList: ArrayList<Movie>
) : RecyclerView.Adapter<WatchedMoviesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var favorite: Boolean = false
        var watched: Boolean = false

        val itemIvMovie = view.findViewById<ImageView>(R.id.ivMovie)!!
        val itemIvTitle = view.findViewById<TextView>(R.id.txtMovieName)!!
        val itemIvDesc = view.findViewById<TextView>(R.id.txtMovieDescription)!!
        val itemBtnDelete = view.findViewById<ImageButton>(R.id.btnDelete)!!
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val moviesRep: MovieRepository = MovieRepository.instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_delete, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]

        Glide.with(holder.itemIvMovie.context).load(Constants.IMAGE_URL + movie.poster_path)
            .into(holder.itemIvMovie)

        holder.itemIvTitle.text = movie.title
        holder.itemIvDesc.text = movie.overview


        holder.favorite = moviesList[position].isFavorite
        holder.watched = moviesList[position].isWatched

        holder.itemBtnDelete.setOnClickListener {
            updateItem(moviesList[position])
            moviesList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, moviesList.size)
        }
    }

    private fun updateItem(movie: Movie) {
        GlobalScope.launch(ioDispatcher){
            val saved = ArrayList(moviesRep.getAllLocalMovies())
            val idx = saved.indexOf(movie)
            if (idx != -1) saved[idx].isWatched = !saved[idx].isWatched
            if (!saved[idx].isFavorite && !saved[idx].isWatched) {
                moviesRep.deleteLocal(saved[idx])
                saved.removeAt(idx)
            }
            moviesRep.replaceAllLocal(saved.toList())
        }
    }

    override fun getItemCount() = moviesList.size
}
