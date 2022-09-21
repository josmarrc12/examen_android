package com.osmar.examen_android.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.osmar.examen_android.common.EALocationManager
import com.osmar.examen_android.data.FirebaseResult
import com.osmar.examen_android.data.models.EALocationPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EALocationRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
    val locationManager = EALocationManager(context)
    val firestore = Firebase.firestore

    fun getUserLocation(): Flow<EALocationPoint> {
        return locationManager.startUpdates()
    }

    suspend fun getUserLocations(): Flow<FirebaseResult<List<EALocationPoint>>> {
        val locationPoints = MutableLiveData<FirebaseResult<List<EALocationPoint>>>()
        val points = mutableListOf<EALocationPoint>()
        val userId  = Firebase.analytics.firebaseInstanceId
        locationPoints.postValue(FirebaseResult.Loading())
        firestore.collection("users").document(userId).collection("directions").get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val data = document.getData()
                    points.add(
                        EALocationPoint(
                            latitude = data["latitude"].toString().toDouble(),
                            longitude = data.get("longitude").toString().toDouble()
                        )
                    )
                }
                locationPoints.postValue(FirebaseResult.Success(points))
            }
            .addOnFailureListener {
                locationPoints.postValue(FirebaseResult.Error(it.message?:""))
            }
        return locationPoints.asFlow()
    }

    suspend fun saveUserLocation(locationPoint: EALocationPoint){
        val userId  = Firebase.analytics.firebaseInstanceId
        val location = hashMapOf(
            "latitude" to locationPoint.latitude,
            "longitude" to locationPoint.longitude
        )
        firestore.collection("users").document(userId).collection("directions").add(location)
    }

}