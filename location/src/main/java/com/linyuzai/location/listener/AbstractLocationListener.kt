package com.linyuzai.location.listener

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

/**
 * Created by linyuzai on 2017/11/20.
 * @author linyuzai
 */
internal abstract class AbstractLocationListener : LocationListener {
    override fun onLocationChanged(location: Location?) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}
}