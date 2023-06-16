package com.example.carbonapp.library.activity_recognition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity

class ActivityRecognitionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)!!
            /*for (event in result.transitionEvents) {
            }*/
            val event = result.transitionEvents[0]
            println("ACTIVITY TYPE: ${event.activityType} - TRANSITION TYPE: ${event.transitionType}")

            val sIntent = Intent(intent.getStringExtra("filter"))
            sIntent.putExtra("activity", event.activityType)
            sIntent.putExtra("transition", event.transitionType)
            LocalBroadcastManager.getInstance(context).sendBroadcast(sIntent)
        }
    }
}