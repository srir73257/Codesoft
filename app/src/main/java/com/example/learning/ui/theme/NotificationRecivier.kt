package com.example.learning.ui.theme

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import com.example.learning.MainActivity
import com.example.learning.R
import kotlin.getValue

private fun channel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val channel = NotificationChannel(
            "notification_channel_1",
            "Reminder",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Show only title"
            enableVibration(false)
            setSound(null, null)
        }


        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}

fun cahnnel2(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val alarm_sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val channel2 = NotificationChannel(
            "notification_channel_2",
            "Alarm Reminder",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminder with Alarms"
            setSound(
                alarm_sound, AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
            )
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 300, 100, 600, 300)
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel2)

    }
}

class NotificationRecivier : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val content = intent.getStringExtra("Content") ?: "Remainder"
        val title = intent.getStringExtra("Title") ?: "Remainder"
        val Id = intent.getIntExtra("Id_code",4)
        channel(context)
        cahnnel2(context)
        val notification1 = NotifictionforChannel1(context, title)
        val norification2 = NotifictionforChannel2(context, title, content)


        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Id, notification1)

        manager.notify(Id+3,norification2)
    }
}


private fun NotifictionforChannel1(context: Context, title: String): Notification {

    val intent = Intent(context, MainActivity::class.java).apply{
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE  or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val notification = NotificationCompat.Builder(
        context,
        "notification_channel_1",
    )
        .setSmallIcon(R.drawable.slide1)
        .setContentTitle(title)
        .setContentText("Open to View")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .build()
    return notification
}

//for cahnnel 2 this
private fun NotifictionforChannel2(context: Context, title: String, content: String): Notification {

    val intent = Intent(context, MainActivity::class.java).apply{
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE  or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val notification = NotificationCompat.Builder(
        context,
        "notification_channel_2",
    )
        .setSmallIcon(R.drawable.slide1)
        .setContentTitle(title)
        .setContentText(content)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(content)
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .build()
    return notification
}


