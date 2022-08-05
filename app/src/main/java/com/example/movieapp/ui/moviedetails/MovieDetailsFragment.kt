package com.example.movieapp.ui.moviedetails

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieDetailsViewModule
    private var favorite: Boolean = false
    private var watched: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[MovieDetailsViewModule::class.java]

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGenresRecyclerViewLayout()

        GlobalScope.launch(Dispatchers.IO) {
            viewModel.movie = viewModel.getMovieDetails()
            withContext(Dispatchers.Main) {
                binding.tvTitle.text = viewModel.movie?.title ?: ""
                binding.tvDescription.text = viewModel.movie?.overview ?: ""
                binding.tvReleaseDate.text = viewModel.movie?.release_date ?: ""

                viewModel.movie?.let { setupGenresBar(it) }

                updateFavoriteButton(favorite)
                updateWatchedButton(watched)
                setUpOnClickListeners()
                loadYtbVideos()
            }
        }
    }

    private fun loadYtbVideos() {
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                viewModel.movie?.videos?.results?.get(0)
                    ?.let { youTubePlayer.loadVideo(findYoutubeTrailer(), 0f) }
            }
        })
    }


    private fun findYoutubeTrailer(): String {
        viewModel.movie?.videos?.results?.let { videoList ->
            for (video in videoList) {
                if (video.type == "Trailer")
                    return video.key
            }
        }
        return ""
    }

    private fun updateFavoriteButton(flag: Boolean) {
        binding.btnFavorite.background.colorFilter = PorterDuffColorFilter(
            when (flag) {
                true -> ContextCompat.getColor(binding.btnFavorite.context, R.color.gold)
                else -> ContextCompat.getColor(binding.btnFavorite.context, R.color.white_18a)
            }, PorterDuff.Mode.SRC
        )
    }

    private fun updateWatchedButton(flag: Boolean) {
        binding.btnWatched.background.colorFilter = PorterDuffColorFilter(
            when (flag) {
                true -> ContextCompat.getColor(binding.btnFavorite.context, R.color.gold)
                else -> ContextCompat.getColor(binding.btnFavorite.context, R.color.white_18a)
            }, PorterDuff.Mode.SRC
        )
    }

    private fun setUpOnClickListeners() {
        binding.btnWatched.setOnClickListener {
            watched = !watched
            updateWatchedButton(watched)
        }

        binding.btnFavorite.setOnClickListener {
            favorite = !favorite
            updateFavoriteButton(favorite)
        }
    }

    private fun setupGenresRecyclerViewLayout() {
        val horizontalRecyclerView = binding.rvFdGenres

        val linearLayoutManager = LinearLayoutManager(this.context)

        with (linearLayoutManager) {
            orientation = LinearLayoutManager.HORIZONTAL
            reverseLayout = false
        }

        horizontalRecyclerView.layoutManager = linearLayoutManager
    }

    private fun setupGenresBar(movieDetails: MovieDetails) {
        with(binding.rvFdGenres) {
            adapter = DetailsGenresBarAdapter(movieDetails.genres)
        }
    }
}