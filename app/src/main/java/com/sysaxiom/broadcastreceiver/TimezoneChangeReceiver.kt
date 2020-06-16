package com.sysaxiom.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast

class TimezoneChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val timezone = intent.getStringExtra("time-zone")
        println(timezone)

        val actionString = intent.getAction()
        if(actionString.equals("android.intent.action.BOOT_COMPLETED")){
            val pendingResult = goAsync()
            println("onReceive: BOOT Action")
            Task(pendingResult, intent).execute()
        }
    }
}

private class Task(internal var pendingResult: BroadcastReceiver.PendingResult, internal var intent: Intent) :
    AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg voids: Void): Void? {

        try {
            println("doInBackground: Work started")
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onPostExecute(aVoid: Void) {
        super.onPostExecute(aVoid)
        println("onPostExecute: Work Finished")
        pendingResult.finish()
    }
}