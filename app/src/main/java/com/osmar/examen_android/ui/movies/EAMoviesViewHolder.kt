package com.osmar.examen_android.ui.movies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osmar.examen_android.data.EAApiConstants
import com.osmar.examen_android.data.models.EAMovie
import com.osmar.examen_android.databinding.ItemEaMovieBinding

class EAMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemEaMovieBinding.bind(view)

    fun render(movie:EAMovie, onClickListener:(EAMovie)->Unit){
        with(binding){
            tvTitle.text = movie.title
            tvDate.text = movie.releaseDate
            tvRated.text = movie.voteAverage.toString()

            Glide.with(ivMovie.context)
                .load(EAApiConstants.SERVER_PATH_IMAGE+movie.backdropPath)
                .into(binding.ivMovie)

            cvMovie.setOnClickListener {
                onClickListener(movie)
            }
        }

    }
}