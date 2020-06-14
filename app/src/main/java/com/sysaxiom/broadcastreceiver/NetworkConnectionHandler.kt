package com.sysaxiom.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import org.greenrobot.eventbus.EventBus
import java.util.HashSet

class NetworkConnectionHandler : BroadcastReceiver() {

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, intent: Intent) {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetwork = connectivityManager?.activeNetwork
            if(activeNetwork!=null){
                val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                if(capabilities!=null){
                    EventBus.getDefault().post(InternetConnectionData(true))
                }else{
                    EventBus.getDefault().post(InternetConnectionData(false))
                }
            } else{
                EventBus.getDefault().post(InternetConnectionData(false))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class InternetConnectionData(
    var isInternetAvailable : Boolean
)