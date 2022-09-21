package com.osmar.examen_android.ui.movies.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.examen_android.data.repositories.EAMoviesRepository
import com.osmar.examen_android.data.models.EAMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EADetailMovieViewModel @Inject constructor(
    private val movieRepository: EAMoviesRepository
):ViewModel() {

    private val _movie =
        MutableStateFlow<EAMovie?>(null)
    val movie: StateFlow<EAMovie?> = _movie

    fun getLocalMovie(id:Long){
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getLocalMovieById(id).collectLatest { movie ->
                Log.e("movie","$movie")
                _movie.value = movie
            }
        }
    }
}