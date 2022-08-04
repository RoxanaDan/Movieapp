package com.example.movieapp.ui.search_movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSearchMoviesBinding
import com.example.movieapp.ui.actors.ActorRepository
import com.example.movieapp.ui.genres.GenreRepository
import com.example.movieapp.ui.moviedetails.MovieDetailsViewModule
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
    private var genreIds = ""
    private var actorIds = ""

    private lateinit var viewModel: MovieDetailsViewModule

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchModelViewModel =
            ViewModelProvider(this).get(SearchMoviesViewModel::class.java)

        _binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(requireActivity())[MovieDetailsViewModule::class.java]

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getQueryParams()
        setSearchTextListener()
    }

    private fun getQueryParams() {
        preselectSavedGenres()
    }

    private fun preselectSavedGenres() {
        GlobalScope.launch(Dispatchers.IO) {
            val savedGenresIds: List<Int> = genreRepository.getAllLocalIds()
            val savedActorIds: List<Int> = actorRepository.getAllLocalIds()
            genreIds = savedGenresIds.joinToString(separator = "|") { "$it" }
            actorIds = savedActorIds.joinToString(separator = "|") { "$it" }
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
                preselectMovies()
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
        rvMovies.adapter = MoviesAdapter(movies,{navigateToMovieDetails()}, viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSearchTextListener() {
        val search = binding.moviesSearch
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if ((newText?.length ?: 0) >= 1) {
                    getSearchedMovies(newText ?: "".toString())
                } else
                    getMovies()
                return false
            }
        })
    }

    fun getSearchedMovies(query: String) {
        GlobalScope.launch(Dispatchers.IO) {
            movies = movieRepository.getSearchedMovies(query)
            withContext(Dispatchers.Main) {
                // moviesLoaded(movies)
                preselectMovies()
                binding.rvMovies.adapter = MoviesAdapter(movies,{navigateToMovieDetails()}, viewModel)

            }
        }
    }

    private fun preselectMovies() {
        GlobalScope.launch(Dispatchers.IO) {
            val saved = movieRepository.getAllLocalMovies()
            withContext(Dispatchers.Main) {
                movies.forEach {
                    val index = saved.indexOf(it)
                    it.isFavorite = (index != -1) && saved[index].isFavorite
                    it.isWatched = (index != -1) && saved[index].isWatched
                }
            }
        }
    }

    private fun navigateToMovieDetails(){
        findNavController().navigate(R.id.movieDetailsFragment)
    }
}