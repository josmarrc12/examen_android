package com.osmar.examen_android.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.osmar.examen_android.data.models.EAMovie
import com.osmar.examen_android.databinding.ItemEaMovieBinding

class EAMoviesAdapter(
    private val moviesList: List<EAMovie>,
    private val onClickListener:(EAMovie)->Unit
):RecyclerView.Adapter<EAMoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EAMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EAMoviesViewHolder(ItemEaMovieBinding.inflate(layoutInflater,parent,false).root)
    }

    override fun onBindViewHolder(holder: EAMoviesViewHolder, position: Int) {
        val item = moviesList[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount() : Int = moviesList.size

}