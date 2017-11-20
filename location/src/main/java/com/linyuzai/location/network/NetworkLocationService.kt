package com.linyuzai.location.network

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.linyuzai.location.builder.LocationBuilder
import com.linyuzai.location.listener.AbstractLocationListener
import com.linyuzai.location.msg.LocationMessage

/**
 * Created by linyuzai on 2017/11/20.
 * @author linyuzai
 */
internal class NetworkLocationService constructor(private val builder: LocationBuilder) : INetwork {
    private val mNetworkLocationListener: LocationListener = object : AbstractLocationListener() {
        override fun onLocationChanged(location: Location?) {
            location ?: return
            builder.manager.removeUpdates(this)
            builder.onReceive?.invoke(location)
        }
    }

    override fun isNetworkEnable(): Boolean = builder.manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    override fun receive() {
        if (!isNetworkEnable()) {
            builder.onError?.invoke(LocationMessage.NETWORK_NOT_AVAILABLE)
            return
        }
        builder.manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, mNetworkLocationListener)
    }

    override fun dispose() {
        builder.manager.removeUpdates(mNetworkLocationListener)
    }
}