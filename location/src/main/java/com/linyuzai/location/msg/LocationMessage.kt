package com.linyuzai.location.msg

/**
 * Created by linyuzai on 2017/11/17.
 * @author linyuzai
 */
object LocationMessage {
    const val TIME_OUT: Int = 0
    const val GPS_NOT_AVAILABLE: Int = 1
    const val NETWORK_NOT_AVAILABLE: Int = 2
    const val GPS_OR_NETWORK_NOT_AVAILABLE: Int = 3

    fun getMessage(msgCode: Int): String = when (msgCode) {
        TIME_OUT -> "定位超时"
        GPS_NOT_AVAILABLE -> "请打开GPS定位"
        NETWORK_NOT_AVAILABLE -> "请打开数据网络或Wi-Fi"
        GPS_OR_NETWORK_NOT_AVAILABLE -> "请打开GPS定位或数据网络或Wi-Fi"
        else -> "未知消息代码"
    }
}