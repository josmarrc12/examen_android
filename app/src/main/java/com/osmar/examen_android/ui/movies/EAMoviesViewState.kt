package com.osmar.examen_android.ui.movies

import com.osmar.examen_android.data.models.EAMovie

sealed class EAMoviesViewState {
    object Start: EAMoviesViewState()
    object Error: EAMoviesViewState()
    object Loading: EAMoviesViewState()
    data class Success(val data: List<EAMovie>?) :EAMoviesViewState()
}