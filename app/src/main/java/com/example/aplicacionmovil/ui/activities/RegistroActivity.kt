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
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.ActivityRegistroBinding
import com.example.aplicacionmovil.ui.utilities.BrotcasterNotification
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth
    private  val TAG = "UCE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistroBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    fun init(){
        createNotification()
        binding.btnRegistrar.setOnClickListener {
            authWithFirebaseEmail(binding.ingresoCorreo.text.toString(),binding.ingresoContrasena.text.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }
    }



    private fun authWithFirebaseEmail(email: String, password: String){
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                    } else {
                        Toast.makeText(
                            baseContext,
                            "No se pudo registrar",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            sendNotificaction()
            Toast.makeText(baseContext,
                "Registro exitoso.",
                Toast.LENGTH_SHORT,
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                baseContext,
                "No se pudo registrar",
                Toast.LENGTH_SHORT,
            ).show()
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
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendNotificaction(){

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val noti= NotificationCompat.Builder(this, CHANNEL)
        noti.setContentTitle("Su usuario a sido creado")
        noti.setContentText("Ya puede usar su cuanta sin problemas")
        noti.setSmallIcon(R.drawable.img1)
        noti.setPriority(NotificationCompat.PRIORITY_HIGH)
        noti.setStyle(
            NotificationCompat
                .BigTextStyle()
                .bigText("Creacion de cuenta realizada, ya puede ingresar a la aplicacion"))
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