package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

//ESTA ES LA CAPA DE PRESENTACION
class MainActivity : AppCompatActivity() {

    //variable que se inicializara despues
    private lateinit var binding:ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding/-funcion que ayuda a inflar "layoutInflater"
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding
        //var button2=binding.button

        //Forma por defecto para inflar
        //setContentView(R.layout.activity_main)

        /*Forma sin binding
        /*El tipo de dato <button> se consigue por viewBindig, la misma que hace la inferencia del objetos desde el .xml*/
        var button1=findViewById<Button>(R.id.button)
        var txtBuscar= findViewById<TextView>(R.id.text1)

        button1.text="INGRESAR"
        button1.setOnClickListener{
            txtBuscar.text="El evento se a ejecutado"
            Toast.makeText(this, "Este es un ejemplo", Toast.LENGTH_SHORT).show()
        }
        var f=Snackbar.make(button1, "Este otro mensaje", Snackbar.LENGTH_SHORT)
        f.show()*/

    }
    override fun onStart(){
        super.onStart()
        initClass()
        initService()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    //Forma binding
    private fun initClass(){
        binding.button.setOnClickListener {
            binding.text1.text="El codigo ejecuta usando binding"

            var f=Snackbar.make(binding.button, "Este otro mensaje usando bindig activity 1", Snackbar.LENGTH_SHORT)
            f.show()
        }
    }

    private fun initService(){

    }
}