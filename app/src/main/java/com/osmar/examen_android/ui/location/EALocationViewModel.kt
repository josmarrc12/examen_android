package com.osmar.examen_android.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.examen_android.data.FirebaseResult
import com.osmar.examen_android.data.models.EALocationPoint
import com.osmar.examen_android.data.models.EAMovie
import com.osmar.examen_android.data.repositories.EALocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EALocationViewModel @Inject constructor(
 private val locationRepository: EALocationRepository
): ViewModel(){

    private val _locations = MutableLiveData<FirebaseResult<List<EALocationPoint>>>()

    val locations:LiveData<FirebaseResult<List<EALocationPoint>>> = _locations


    fun getLocations() = viewModelScope.launch(Dispatchers.IO){
        locationRepository.getUserLocations().collect{
            _locations.postValue(it)
        }
    }
}