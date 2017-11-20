package com.linyuzai.location.gps

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.linyuzai.location.listener.AbstractLocationListener
import com.linyuzai.location.builder.LocationBuilder
import com.linyuzai.location.msg.LocationMessage

/**
 * Created by linyuzai on 2017/11/20.
 * @author linyuzai
 */
internal class GPSLocationService constructor(private val builder: LocationBuilder) : IGPS {
    private val mGPSLocationListener: LocationListener = object : AbstractLocationListener() {
        override fun onLocationChanged(location: Location?) {
            location ?: return
            builder.manager.removeUpdates(this)
            builder.onReceive?.invoke(location)
        }
    }

    override fun isGPSEnable(): Boolean = builder.manager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    override fun receive() {
        if (!isGPSEnable()) {
            builder.onError?.invoke(LocationMessage.GPS_NOT_AVAILABLE)
            return
        }
        builder.manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, mGPSLocationListener)
    }

    override fun dispose() {
        builder.manager.removeUpdates(mGPSLocationListener)
    }
}