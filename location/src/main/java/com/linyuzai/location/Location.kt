package com.linyuzai.location

import android.content.Context
import com.linyuzai.location.builder.ILocationBuilder
import com.linyuzai.location.builder.LocationBuilder

/**
 * Created by linyuzai on 2017/11/15.
 * @author linyuzai
 */
object Location {
    fun with(context: Context): ILocationBuilder = LocationBuilder(context)
}