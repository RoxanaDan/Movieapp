package com.example.movieapp.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSavedMoviesBinding
import com.example.movieapp.ui.movies.Movie
import com.example.movieapp.ui.movies.MovieRepository
import com.example.movieapp.ui.tab_activity.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedMoviesFragment : Fragment(R.layout.fragment_saved_movies) {
    private var movies = ArrayList<Movie>()
    private var adapter: AdapterTabPager? = null
    private var movieRepository: MovieRepository = MovieRepository.instance

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        GlobalScope.launch(Dispatchers.IO) {
            movies = ArrayList(movieRepository.getAllLocalMovies())
            withContext(Dispatchers.Main) {
                val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
                val tabs: TabLayout = view.findViewById(R.id.tabs)
                adapter = activity?.let { AdapterTabPager(it) }
                adapter?.addFragment(FavoriteFragment(movies), "favorites")
                adapter?.addFragment(WatchedFragment(movies), "watched")

                viewPager.adapter = adapter
                viewPager.currentItem = 0

                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = adapter?.getPageTitle(position)
                }.attach()
            }
        }
    }
}