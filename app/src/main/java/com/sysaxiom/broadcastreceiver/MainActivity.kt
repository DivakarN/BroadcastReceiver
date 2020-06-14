package com.sysaxiom.broadcastreceiver

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    private var networkConnectionHandler: NetworkConnectionHandler = NetworkConnectionHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val filter = IntentFilter()
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE") //or any intent filter you want
            registerReceiver(networkConnectionHandler, filter)
        } catch (e: Exception) {
            Log.d("NetworkHandler", e.toString())
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(internetConnectionData: InternetConnectionData) {
        Toast.makeText(this, "Network ${internetConnectionData.isInternetAvailable}", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkConnectionHandler)
    }
}
