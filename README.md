# BroadcastReceiver

Broadcast Receiver:
-------------------

Broadcast Receiver is a bridge between android systems and android application.

Apps can send or receive messages known as broadcast messages from android systems and other android apps when an event of interest occurs.

It is similar to the publish-subscribe pattern.

Broadcast message itself is wrapped in an intent.

Action string identifies the event that occured (example: android.intent.action.AIRPLANE_MODE)

List of events:
---------------

1) Boot completed
2) Phone call
3) Battery low 
4) Airplane mode changed
5) SMS received
6) Power connected

Receiving Broadcast:
--------------------

Registration of Broadcast Receiver
There are two ways to register a Broadcast Receiver

one is Static and the other Dynamic.

1) Static: Use <receiver> tag in your Manifest file. (AndroidManifest.xml)

If your app targets API level 26 or higher, you cannot use the manifest to declare a receiver for implicit broadcasts (broadcasts that do not target your app specifically), except for a few implicit broadcasts that are exempted from that restriction. In most cases, you can use scheduled jobs instead.
That means if you really need broadcast receiver then you should dynamically register and unregister it by code

class TimezoneChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timezone = intent.getStringExtra("time-zone")
        println(timezone)
    }
}

<receiver
    android:name=".TimezoneChangeReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.TIMEZONE_CHANGED" />
    </intent-filter>
</receiver>


2) Dynamic: Use Context.registerReceiver () method to dynamically register an instance.

private var timezoneChangeReceiver = TimezoneChangeReceiver()

override fun onStart() {
    try {
        val filter = IntentFilter()
        filter.addAction("android.intent.action.TIMEZONE_CHANGED") //or any intent filter you want
        registerReceiver(timezoneChangeReceiver, filter)
    } catch (e: Exception) {
        Log.d("NetworkHandler", e.toString())
    }
    super.onStart()
}

override fun onDestroy() {
    super.onDestroy()
    unregisterReceiver(networkConnectionHandler)
}

Difference:
-----------

Static - Implementation are in manifest, android system can initiate processes and run your boardcast receiver even if application is not running.

Dynamic - Implementation are in code, boardcast receiver runs only when your app is running up to that registration line.
