package com.linyuzai.demo4location

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.linyuzai.location.Location
import com.linyuzai.location.builder.ILocationBuilder
import com.linyuzai.location.gps.IGPS
import com.linyuzai.location.msg.LocationMessage
import com.linyuzai.location.network.INetwork
import com.tbruyelle.rxpermissions2.RxPermissions

class MainActivity : AppCompatActivity() {

    private lateinit var mText: TextView
    private lateinit var mGPSButton: StateButton
    private lateinit var mNetworkButton: StateButton
    private lateinit var mRxPermissions: RxPermissions
    private lateinit var mBuilder: ILocationBuilder
    private lateinit var mGPS: IGPS
    private lateinit var mNetwork: INetwork

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mText = findViewById(R.id.text) as TextView
        mGPSButton = findViewById(R.id.gps) as StateButton
        mNetworkButton = findViewById(R.id.network) as StateButton

        mBuilder = Location.with(this).onError {
            mText.text = LocationMessage.getMessage(it)
        }.onReceive {
            mText.text = "${it?.latitude},${it?.longitude}"
        }
        mGPS = mBuilder.gps()
        mNetwork = mBuilder.network()
        mRxPermissions = RxPermissions(this)

        mGPSButton.defaultText("开始GPS定位").activeText("停止GPS定位").setBeforeFilter { _, _ ->
            if (mGPS.isGPSEnable())
                return@setBeforeFilter true
            else
                mText.text = LocationMessage.getMessage(LocationMessage.GPS_NOT_AVAILABLE)
            //Toast.makeText(this, isPass.toString(), Toast.LENGTH_SHORT).show()
            false
        }.setOnStateClickListener { _, state ->
            mRxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe {
                if (it) {
                    mGPSButton.updateState()
                    if (state == StateButton.DEFAULT)
                        mGPS.receive()
                    else if (state == StateButton.ACTIVE)
                        mGPS.dispose()
                } else mText.text = "权限被拒绝"
            }
        }
        mNetworkButton.defaultText("开始网络定位").activeText("停止网络定位").setBeforeFilter { _, _ ->
            if (mNetwork.isNetworkEnable())
                return@setBeforeFilter true
            else
                mText.text = LocationMessage.getMessage(LocationMessage.NETWORK_NOT_AVAILABLE)
            false
        }.setOnStateClickListener { _, state ->
            mRxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe {
                if (it) {
                    if (state == StateButton.DEFAULT)
                        mNetwork.receive()
                    else if (state == StateButton.ACTIVE)
                        mNetwork.dispose()
                } else mText.text = "权限被拒绝"
            }
        }
    }
}
