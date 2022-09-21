package com.osmar.examen_android.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.osmar.examen_android.data.models.EALocationPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.lifecycle.asFlow

private const val TAG = "LocationManager"

class EALocationManager(
    val context: Context
) {
    private val onLocationUpdate = MutableLiveData<EALocationPoint>()
    private val task: Task<LocationSettingsResponse>
    private val fusedLocationClient: FusedLocationProviderClient
    private val locationRequest: LocationRequest

    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest = LocationRequest.create().apply {
            interval = 1800000//30 minutes
            fastestInterval = 1800000//30 minutes
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context)
        task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            task.addOnSuccessListener { locationRequest-> Log.d(TAG, "onLocationSuccess: Location success") }
            task.addOnFailureListener { exeption-> Log.e(TAG, "onLocation failed: Location failed")}
        }
    }

    fun startUpdates(): Flow<EALocationPoint> {
        if(ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
            Log.e(TAG, "startUpdates: Permission denied")
        }else{
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper())
        }
        return onLocationUpdate.asFlow()
    }

    private val locationCallBack = object:LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            GlobalScope.launch {
                onLocationUpdate.postValue(
                    EALocationPoint(
                        latitude = locationResult.lastLocation.latitude,
                        longitude = locationResult.lastLocation.longitude
                    )
                )
            }
        }
    }
}