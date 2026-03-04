package com.example.bikeweatherforecastapp.data.services.notifications


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.core.app.NotificationCompat
import com.example.bikeweatherforecastapp.R
import com.example.bikeweatherforecastapp.navigation.MainActivity
import androidx.core.net.toUri


class NotificationHelper(private val context: Context) {

    companion object{
        const val CHANNEL_ID = "weather_channel"
    }
    fun showNotification() {
        val title = "Are you riding today?"
        val message = "Weather conditions look ideal for cycling today."
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val intent = Intent(context, MainActivity::class.java).apply {
            flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent= PendingIntent.getActivity(context,0,intent,
            PendingIntent.FLAG_IMMUTABLE)

        val channel = NotificationChannel(CHANNEL_ID,title,importance).apply {
            description = message
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.bike)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.bikee
                )
            )
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager: NotificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(1,notification)

    }
    fun batteryOptimization(): Boolean{
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    fun requestDisableBatteryOptimizations() {

        val intent = Intent(
            Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        )
        intent.data = "package:${context.packageName}".toUri()

        context.startActivity(intent)
    }



}



