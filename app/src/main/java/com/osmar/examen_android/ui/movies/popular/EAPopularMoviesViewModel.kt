package com.osmar.examen_android.ui.movies.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.examen_android.common.EAConstants
import com.osmar.examen_android.data.EANetworkResult
import com.osmar.examen_android.data.repositories.EAMoviesRepository
import com.osmar.examen_android.data.models.EAMovie
import com.osmar.examen_android.ui.movies.EAMoviesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EAPopularMoviesViewModel @Inject constructor(
    private val moviesRepository: EAMoviesRepository
): ViewModel(){


    private val _uiState =
        MutableStateFlow<EAMoviesViewState>(EAMoviesViewState.Start)
    val uiState: StateFlow<EAMoviesViewState> = _uiState

    fun getPopularMovies(){
        viewModelScope.launch {
            moviesRepository.getPopularMovies().collectLatest {
                when(it.status){
                    EANetworkResult.Status.LOADING ->{}
                    EANetworkResult.Status.SUCCESS ->{
                            updateMovies(it.data?.movies)
                        _uiState.value = EAMoviesViewState.Success(it.data?.movies)
                        Log.e("SUCCESS","${it.data}")
                    }
                    EANetworkResult.Status.ERROR ->{
                        getLocalMovies()

                        Log.e("Error","${it.message}")
                    }
                }
            }
        }
    }


   private fun updateMovies(listMovies: List<EAMovie>?){
       listMovies?.let {
           val popularList :MutableList<EAMovie> = listMovies.toMutableList()
           popularList.forEach{
               it.type = EAConstants.TYPE_POPULAR
           }
           viewModelScope.launch(Dispatchers.IO){
               moviesRepository.updateMovies(popularList)
           }
       }
    }

    private fun getLocalMovies(){
        viewModelScope.launch(Dispatchers.IO){
            moviesRepository.getLocalPopularMovies().collectLatest { localMovies ->
                if (localMovies == null){
                    _uiState.value = EAMoviesViewState.Error
                }else{
                    _uiState.value = EAMoviesViewState.Success(localMovies)
                }
            }
        }
    }

}