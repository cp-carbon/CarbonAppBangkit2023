package com.example.carbonapp.library.activity_recognition

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionRequest
import com.google.android.gms.location.DetectedActivity

class ActivityRecognitionService : Service() {

    private data class MonitoredActivity(
        val detectedActivity: Int,
        val activityTransition: Int,
    )

    private val monitoredActivities = listOf(
        MonitoredActivity(DetectedActivity.IN_VEHICLE, ActivityTransition.ACTIVITY_TRANSITION_ENTER),
        MonitoredActivity(DetectedActivity.ON_BICYCLE, ActivityTransition.ACTIVITY_TRANSITION_ENTER),
        MonitoredActivity(DetectedActivity.ON_FOOT, ActivityTransition.ACTIVITY_TRANSITION_ENTER),
        MonitoredActivity(DetectedActivity.WALKING, ActivityTransition.ACTIVITY_TRANSITION_ENTER),
        MonitoredActivity(DetectedActivity.RUNNING, ActivityTransition.ACTIVITY_TRANSITION_ENTER),
        MonitoredActivity(DetectedActivity.STILL, ActivityTransition.ACTIVITY_TRANSITION_ENTER),
    )

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return setupActivityRecognition(intent)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun setupActivityRecognition(intent: Intent?): Int {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return START_NOT_STICKY
        }

        val transitions = monitoredActivities.map {
            ActivityTransition.Builder()
                .setActivityType(it.detectedActivity)
                .setActivityTransition(it.activityTransition)
                .build()
        }

        val request = ActivityTransitionRequest(transitions)
        val rIntent = Intent(this, ActivityRecognitionReceiver::class.java)
        if (intent != null) rIntent.putExtra("filter", intent.getStringExtra("filter"))
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            202,
            rIntent,
            PendingIntent.FLAG_MUTABLE)

        val task = ActivityRecognition.getClient(this)
            .requestActivityTransitionUpdates(request, pendingIntent)

        task.addOnSuccessListener {
            println("ACTIVITY RECOGNITION STARTED")
        }

        task.addOnFailureListener { e: Exception ->
            println("ACTIVITY RECOGNITION FAILED TO START")
            e.printStackTrace()
        }

        return START_STICKY
    }
}