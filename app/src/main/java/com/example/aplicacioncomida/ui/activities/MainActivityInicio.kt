package com.example.aplicacioncomida.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.aplicacioncomida.R
import com.example.aplicacioncomida.databinding.ActivityMainInicioBinding

class MainActivityInicio : AppCompatActivity() {

    private lateinit var binding:ActivityMainInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE","Entrando a create")
        binding=ActivityMainInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onStart(){
        super.onStart()
        //Funcion de ambiente: permite recuperar informacion de otra acticity
        //extras.!! ->para confirmar que una dato nunca va hacer nulo
        //it?. -> para decir que un dato puede ser nulo
        var name:String=""
        intent.extras.let {
            name=it?.getString("var1")!!

        }
        Log.d("UCE","Entrando a start: ${name}")
        binding.textname.text="Bienvenido: "+name.toString()
        binding.boton1.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}