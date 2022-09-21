package com.osmar.examen_android

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.osmar.examen_android.common.EALocationService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var locationService:EALocationService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLocationPermission()
    }


    private fun getLocationPermission(){
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            if(isGranted){
                startService()
            }else{
                Toast.makeText(this, getString(R.string.ea_permision_denied), Toast.LENGTH_SHORT).show()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startService()
                }
                shouldShowRequestPermissionRationale(getString(R.string.ea_location))->{
                    AlertDialog.Builder(this).setTitle(R.string.ea_location_permission).setMessage(R.string.ea_location_permission_details).show()
                }
                else ->{
                    permissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            }
        }else{
            startService()
        }
    }

    private fun startService(){
        val intent = Intent(this, EALocationService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }


    private val serviceConnection: ServiceConnection = object : ServiceConnection {//Inicia comunicacion con proceso de ubicación
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        locationService = (service as EALocationService.LocalBinder).getService()
    }

        override fun onServiceDisconnected(name: ComponentName?) {//finaliza  comunicación con proceso de ubicación
            locationService = null
        }

    }
}