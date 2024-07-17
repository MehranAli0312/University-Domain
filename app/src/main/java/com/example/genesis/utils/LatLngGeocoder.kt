package com.example.genesis.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.example.genesis.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

object LatLngGeocoder {

    suspend fun getLatLngFromLocationName(context: Context, address: String): LatLngResult? {
        return withContext(Dispatchers.IO) {
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addressList = geocoder.getFromLocationName(address, 1)
                if (!addressList.isNullOrEmpty()) {
                    val address = addressList[0]
                    LatLngResult(address.latitude, address.longitude)
                } else {
                    null
                }
            } catch (e: IOException) {
                Log.e("GeocoderAddress", context.resources.getString(R.string.app_name))
                null
            }
        }
    }
}

data class LatLngResult(val latitude: Double, val longitude: Double)
