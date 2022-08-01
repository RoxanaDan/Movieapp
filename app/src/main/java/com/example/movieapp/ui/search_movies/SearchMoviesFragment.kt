package com.example.movieapp.ui.search_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentSearchMoviesBinding
import com.example.movieapp.ui.actors.ActorRepository
import com.example.movieapp.ui.genres.GenreRepository
import com.example.movieapp.ui.movies.Movie
import com.example.movieapp.ui.movies.MovieRepository
import com.example.movieapp.ui.movies.MoviesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchMoviesFragment : Fragment() {

    private var _binding: FragmentSearchMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var movies: List<Movie> = emptyList()
    private val movieRepository = MovieRepository.instance
    private val genreRepository = GenreRepository.instance
    private val actorRepository = ActorRepository.instance
    private  var genreIds = ""
    private  var actorIds = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchModelViewModel =
            ViewModelProvider(this).get(SearchMoviesViewModel::class.java)

        _binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getQueryParams()
    }

    private fun getQueryParams() {
        preselectSavedGenres()
    }

    private fun preselectSavedGenres() {
            GlobalScope.launch(Dispatchers.IO) {
                val savedGenresIds: List<Int> = genreRepository.getAllLocalIds()
                val savedActorIds: List<Int> = actorRepository.getAllLocalIds()
                genreIds = savedGenresIds.joinToString(separator ="|" ) {"$it"}
                actorIds = savedActorIds.joinToString(separator ="|" ) {"$it"}
                Log.d("Test", "Rezultat: $genreIds")
                withContext(Dispatchers.Main) {
                    getMovies()
                }
            }
        }

    private fun getMovies() {
        GlobalScope.launch(Dispatchers.IO) {
            movies = movieRepository.getAllRemoteMovies()
            withContext(Dispatchers.Main) {
                moviesLoaded(movies)
            }
        }
    }

    private fun moviesLoaded(movies: List<Movie>) {
        setUpRecycleView()
    }

    private fun setUpRecycleView() {
        val rvMovies = binding.rvMovies
        rvMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvMovies.adapter = MoviesAdapter(movies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}