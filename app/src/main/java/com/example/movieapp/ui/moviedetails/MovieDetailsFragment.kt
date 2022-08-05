package com.example.movieapp.ui.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsFragment : Fragment() {
    private var _binding : FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MovieDetailsViewModule

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

        GlobalScope.launch (Dispatchers.IO) {
            viewModel.movie = viewModel.getMovieDetails()
            withContext(Dispatchers.Main){
                binding.tvTitle.text = viewModel.movie?.title ?: ""
                binding.tvDescription.text = viewModel.movie?.overview ?: ""
                binding.tvReleaseDate.text = viewModel.movie?.release_date ?: ""
                loadYtbVideos()
            }
        }
    }

    private fun loadYtbVideos(){
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                viewModel.movie?.videos?.results?.get(0)?.let { youTubePlayer.loadVideo(findYoutubeTrailer(), 0f) }
            }
        })
    }


    private fun findYoutubeTrailer() : String {
        viewModel.movie?.videos?.results?.let{ videoList ->
            for(video in videoList) {
                if(video.type == "Trailer")
                    return video.key
            }
        }
        return ""
    }
}
