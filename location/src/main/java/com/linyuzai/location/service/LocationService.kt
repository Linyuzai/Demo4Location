package com.linyuzai.location.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.linyuzai.location.builder.LocationBuilder
import com.linyuzai.location.msg.LocationMessage

/**
 * Created by linyuzai on 2017/11/15.
 * @author linyuzai
 */
internal class LocationService constructor(private val builder: LocationBuilder) {
    private var isGPSLocated: Boolean = false
    private var location: Location? = null

    private val mGPSLocationListener: LocationListener = object : ULocationListener() {
        override fun onLocationChanged(location: Location?) {
            location ?: return
            if (isNetworkEnable())
                builder.manager.removeUpdates(mNetworkLocationListener)
            this@LocationService.location = location
            isGPSLocated = true
            builder.manager.removeUpdates(this)
        }
    }

    private val mNetworkLocationListener: LocationListener = object : ULocationListener() {
        override fun onLocationChanged(location: Location?) {
            location ?: return
            builder.manager.removeUpdates(this)
            if (isGPSLocated)
                return
            this@LocationService.location = location
        }
    }

    fun receive() {
        if (!isGPSEnable() && !isNetworkEnable()) {
            builder.onError?.invoke(LocationMessage.GPS_OR_NETWORK_NOT_AVAILABLE)
            return
        }
        if (isGPSEnable())
            builder.manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, mGPSLocationListener)
        if (isNetworkEnable())
            builder.manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, mNetworkLocationListener)
        Handler(Looper.getMainLooper()).postDelayed({
            builder.manager.removeUpdates(mGPSLocationListener)
            builder.manager.removeUpdates(mNetworkLocationListener)
            if (location == null)
                builder.onError?.invoke(LocationMessage.TIME_OUT)
            else
                builder.onReceive?.invoke(location)
        }, builder.timeout)
    }

    private fun isGPSEnable(): Boolean = builder.manager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun isNetworkEnable(): Boolean = builder.manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    abstract class ULocationListener : LocationListener {
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        override fun onProviderEnabled(provider: String?) {}

        override fun onProviderDisabled(provider: String?) {}
    }
}