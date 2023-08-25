package com.example.aplicacionmovil.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.ui.activities.SecondActivity

class BrotcasterNotification: BroadcastReceiver() {

    val CHANNEL: String ="Notificacion"

    override fun onReceive(context: Context, intent: Intent) {

        val intent = Intent(context, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)



        val noti= NotificationCompat.Builder(context, CHANNEL)
        noti.setContentTitle("Primera notificacion")
        noti.setContentText("MI primera notificaciones en android")
        noti.setSmallIcon(R.drawable.img2)
        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(
            NotificationCompat
                .BigTextStyle()
                .bigText("Esta es una notificacion para recordar que esta es mi primera implementacipn de notificacion"))

        noti.setContentIntent(pendingIntent)
        val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            1,
            noti.build()
        )
    }
}