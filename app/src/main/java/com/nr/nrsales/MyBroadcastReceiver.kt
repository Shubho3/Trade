package com.nr.nrsales;

import android.content.BroadcastReceiver;
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Update the UI here, for example, by sending a broadcast to the activity
        val updateIntent = Intent("com.example.UPDATE_UI_ACTION")
        context?.sendBroadcast(updateIntent)
    }
}
