package com.example.movieapp.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.RvDetailsGenreBinding
import com.example.movieapp.ui.genres.Genre

class DetailsGenresBarAdapter(
    private val items: List<Genre>
) : RecyclerView.Adapter<DetailsGenresBarAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvDetailsGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvDetailsGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.tvItemName.text = this.name
            }
        }
    }

    override fun getItemCount() = items.size
}