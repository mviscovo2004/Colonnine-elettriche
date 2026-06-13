package it.univaq.colonnine_elettriche.ui.screens.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*

class LocationHelper(context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    
    // Nelle versioni recenti (21.x+), il Builder accetta l'intervallo come parametro L (Long)
    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
        .setMinUpdateIntervalMillis(2000L)
        .build()

    @SuppressLint("MissingPermission")
    fun start(callback: LocationCallback) {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        )
    }

    fun stop(callback: LocationCallback) {
        fusedLocationClient.removeLocationUpdates(callback)
    }
}
