package com.example.dispositivosmoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button1=findViewById<Button>(R.id.button)
        var txtBuscar= findViewById<TextView>(R.id.text1)
        button1.text="INGRESAR"
        button1.setOnClickListener{
            txtBuscar.text="El evento se a ejecutado"
            Toast.makeText(this, "Este es un ejemplo", Toast.LENGTH_SHORT).show()
        }
        var f=Snackbar.make(button1, "Este otro mensaje", Snackbar.LENGTH_SHORT)
        f.setBackgroundTint(85).show()
    }
}