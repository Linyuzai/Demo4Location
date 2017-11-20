package com.linyuzai.location.builder

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.linyuzai.location.gps.GPSLocationService
import com.linyuzai.location.gps.IGPS
import com.linyuzai.location.network.INetwork
import com.linyuzai.location.network.NetworkLocationService
import com.linyuzai.location.service.LocationService

/**
 * Created by linyuzai on 2017/11/16.
 * @author linyuzai
 */
internal class LocationBuilder constructor(context: Context) : ILocationBuilder {
    internal val manager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    internal var timeout: Long = 5L
    //internal var lateReceive: Boolean = false
    //internal var isGPSEnable: Boolean = false
    //internal var isNetworkEnable: Boolean = false
    internal var onError: ((msgCode: Int) -> Unit)? = null
    internal var onReceive: ((location: Location?) -> Unit)? = null

    override fun timeout(milliseconds: Long): ILocationBuilder = apply { this.timeout = milliseconds }

    //override fun lateReceive(): ILocationBuilder = apply { lateReceive = true }

    //override fun enableGPS(): ILocationBuilder = apply { isGPSEnable = true }

    //override fun enableNetwork(): ILocationBuilder = apply { isNetworkEnable = true }

    override fun onError(onError: (msgCode: Int) -> Unit): ILocationBuilder = apply { this.onError = onError }

    override fun onReceive(onReceive: (location: Location?) -> Unit): ILocationBuilder = apply { this.onReceive = onReceive }

    override fun location() = LocationService(this).receive()

    override fun gps(): IGPS = GPSLocationService(this)

    override fun network(): INetwork = NetworkLocationService(this)
}