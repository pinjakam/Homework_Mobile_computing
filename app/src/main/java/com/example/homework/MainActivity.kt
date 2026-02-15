package com.example.homework

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    private lateinit var sensorManager: SensorManager
    var movementValue = 0f
    private var lastMovementTime = System.currentTimeMillis()

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        createNotificationChannel()
        startAccelerometer()
        startInactivityChecker()

        setContent {
            AppNavigation(activity = this, viewModel = viewModel)
        }
    }

    private fun startAccelerometer() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event ?: return

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                movementValue = sqrt(x * x + y * y + z * z)

                // Increase threshold to avoid micro-movement resets
                if (movementValue > 2.5f) {
                    lastMovementTime = System.currentTimeMillis()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun startInactivityChecker() {
        lifecycleScope.launch {
            while (true) {
                delay(1000) // check every second

                val now = System.currentTimeMillis()
                val inactiveSeconds = (now - lastMovementTime) / 1000

                if (inactiveSeconds >= 10) {
                    val text = "Inactivity alert at ${java.time.LocalTime.now()}"
                    viewModel.addNotification(text)
                    sendInactivityNotification(text)

                    lastMovementTime = System.currentTimeMillis()
                }
            }
        }
    }

    fun sendInactivityNotification(text: String) {

        // When tapped, open MainActivity
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "inactivity_channel")
            .setContentTitle("Inactivity Alert")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // important
            .build()

        NotificationManagerCompat.from(this).notify(1001, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "inactivity_channel",
                "Inactivity Alerts",
                NotificationManager.IMPORTANCE_HIGH // MUST be HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
