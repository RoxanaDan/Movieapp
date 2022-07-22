package com.example.movieapp.ui.actors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R

class ActorsAdapter(private val actorList: List<Actor>) :
    RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val actorName: TextView = view.findViewById(R.id.tvName)
        val parentView: ConstraintLayout = view.findViewById(R.id.parent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_actor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = actorList[position]
        holder.actorName.text = actor.name
        holder.parentView.setOnClickListener {
            actor.isSelected = !actor.isSelected

            holder.parentView.setBackgroundColor(
                when (actor.isSelected) {
                    true -> ContextCompat.getColor(holder.parentView.context, R.color.gold)
                    else -> ContextCompat.getColor(holder.parentView.context, R.color.white)
                })
        }
    }
    override fun getItemCount() = actorList.size
}