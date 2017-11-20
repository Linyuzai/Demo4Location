package com.linyuzai.location.network

import com.linyuzai.location.service.IService

/**
 * Created by linyuzai on 2017/11/20.
 * @author linyuzai
 */
interface INetwork : IService {
    fun isNetworkEnable(): Boolean
}