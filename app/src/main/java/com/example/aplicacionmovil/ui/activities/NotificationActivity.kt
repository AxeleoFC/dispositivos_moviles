package com.example.aplicacionmovil.ui.activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.ActivityNotificationBinding
import com.example.aplicacionmovil.ui.utilities.BrotcasterNotification
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNotificacion.setOnClickListener {
            createNotification()
            sendNotificaction()
        }
        binding.btnHora.setOnClickListener {
            val calendar= Calendar.getInstance()
            val hora=binding.reloj.hour
            val minuto=binding.reloj.minute
            Toast.makeText(this,"La hora del recordatorio sera a las $hora con $minuto", Toast.LENGTH_SHORT).show()
            calendar.set(Calendar.HOUR, hora)
            calendar.set(Calendar.MINUTE, minuto)
            calendar.set(Calendar.SECOND, 0)
            sendNotificactionTimePicker(calendar.timeInMillis)
        }

    }

    val CHANNEL: String ="Cotificacion"

    private fun createNotification(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name = "Variedades"
            val descriptionText= "Notificacion simples de variables"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL, name,importance).apply {
                description=descriptionText
            }
            val notificationManager: NotificationManager=
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotificaction(){

        val intent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val noti= NotificationCompat.Builder(this, CHANNEL)
        noti.setContentTitle("Primera notificacion")
        noti.setContentText("MI primera notificaciones en android")
        noti.setSmallIcon(R.drawable.img1)
        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(
            NotificationCompat
                .BigTextStyle()
                .bigText("Esta es una notificacion para recordar que esta es mi primera implementacipn de notificacion"))

        noti.setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)){
            notify(1,noti.build())
        }
    }

    @SuppressLint("ServiceCast")
    fun sendNotificactionTimePicker(time:Long){
        val miIntent=Intent(applicationContext, BrotcasterNotification::class.java)
        val myPendingIntent= PendingIntent.getBroadcast(
            applicationContext,
            0,
            miIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager=getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, myPendingIntent)
    }
}