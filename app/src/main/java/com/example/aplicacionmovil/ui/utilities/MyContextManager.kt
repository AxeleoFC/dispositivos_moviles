package com.example.aplicacionmovil.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient

class MyContextManager(val context: Context) {

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var client: SettingsClient
    private lateinit var locationSettingsRequest: LocationSettingsRequest

    private fun initVars() {
        if(context!=null){
            client= LocationServices.getSettingsClient(context!!)
        }
    }
    fun getClientLocation(): SettingsClient{
        return client
    }


}