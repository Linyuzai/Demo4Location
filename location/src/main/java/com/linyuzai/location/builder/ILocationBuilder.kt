package com.linyuzai.location.builder

import android.location.Location
import com.linyuzai.location.gps.IGPS
import com.linyuzai.location.network.INetwork

/**
 * Created by linyuzai on 2017/11/16.
 * @author linyuzai
 */
interface ILocationBuilder {

    fun timeout(milliseconds: Long): ILocationBuilder

    //fun lateReceive(): ILocationBuilder

    //fun enableGPS(): ILocationBuilder

    //fun enableNetwork(): ILocationBuilder

    fun onError(onError: (msgCode: Int) -> Unit): ILocationBuilder

    fun onReceive(onReceive: (location: Location?) -> Unit): ILocationBuilder

    fun location()

    fun gps(): IGPS

    fun network(): INetwork
}