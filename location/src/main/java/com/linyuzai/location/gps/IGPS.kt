package com.linyuzai.location.gps

import com.linyuzai.location.service.IService

/**
 * Created by linyuzai on 2017/11/20.
 * @author linyuzai
 */
interface IGPS : IService {
    fun isGPSEnable(): Boolean
}