package com.osmar.examen_android.common

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.LifecycleService
import com.osmar.examen_android.MainActivity
import com.osmar.examen_android.R
import com.osmar.examen_android.data.repositories.EALocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PACKAGE_NAME:String = "com.osmar.examen_android.common"
private const val CHANNEL_ID:String="location"
private const val NOTIFICATION_ID:Int = 42
private const val EXTRA_STARTED_FROM_NOTIFICATION:String = PACKAGE_NAME+".started_from_notification"

private const val TAG = "LocationService"

@AndroidEntryPoint
class EALocationService : LifecycleService(){

    private val supervisorJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main+supervisorJob)
    private lateinit var notificationManager: NotificationManager
    private lateinit var serviceHandler: Handler
    private val binder = LocalBinder()
    @Inject
    lateinit var locationRepository: EALocationRepository

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //Se genera un canal de notificaciones si la aplicación esta corriendo sobre android 8 o superior
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name = getString(R.string.app_name)
            val nChannel = NotificationChannel(CHANNEL_ID,name,NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(nChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supervisorJob.cancel()
    }

    fun getLocation() = serviceScope.launch(Dispatchers.IO) {
        locationRepository.getUserLocation().collect{
            Log.d(TAG, "getLocation: "+it.latitude+","+it.longitude)
            locationRepository.saveUserLocation(it)
        }
    }

    fun getNotificacion(): Notification {
        val intent = Intent(this,EALocationService::class.java)
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION,true)
        val servicePendingIntent: PendingIntent = PendingIntent
            .getService(this,0, Intent(this, MainActivity::class.java),0)
        val notification: Notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this,CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.ea_updatingLocation))
                .setSmallIcon(R.drawable.ea_ic_gps)
                .setContentIntent(servicePendingIntent)
                .setTicker("Ticker")
                .build()
        } else {
            Notification.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.ea_updatingLocation))
                .setSmallIcon(R.drawable.ea_ic_gps)
                .setContentIntent(servicePendingIntent)
                .setTicker("Ticker")
                .build()
        }
        return notification
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        startForeground(NOTIFICATION_ID,getNotificacion())
        getLocation()
        return binder
    }

    inner class LocalBinder: Binder(){
        fun getService(): EALocationService {
            return this@EALocationService
        }
    }
}