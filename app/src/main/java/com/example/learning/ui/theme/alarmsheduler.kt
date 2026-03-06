package com.example.learning.ui.theme

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresPermission
import android.provider.Settings



@RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
fun Alarmsheduler(
    context: Context,
    hour: Int,
    min: Int,
    Id_Code : Int,
    content: String,
    title : String
){

    val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            return
        }
    }
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY,hour)
        set(Calendar.MINUTE,min)
        set(Calendar.SECOND,0)

        if (timeInMillis < System.currentTimeMillis()){
            add(Calendar.DAY_OF_MONTH,1)
        }
    }

    val intent = Intent(context, NotificationRecivier::class.java).apply {

        putExtra("Content",content)
        putExtra("Title",title)
        putExtra("Id_code",Id_Code)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        Id_Code,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )



    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent,
    )


}