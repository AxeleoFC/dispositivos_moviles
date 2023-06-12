package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

//esta clase hereda de AppCompatActivity
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //reescribir la funcion onCreate que hereda de  AppCompactActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initClass() {
        binding.btnIngresar.setOnClickListener {
            //binding.txtBuscar.text = "El codigo ejecuta correctamente"
            //Toast.makeText(this,
            //   "Este es un ejemplo",
            //    Toast.LENGTH_SHORT)
            //    .show()

            /* var f=Snackbar.make(binding.boton1,
                 "Este es otro mensaje",
                 Snackbar.LENGTH_LONG)
             //f.setBackgroundTint(R.color.black).show()
             f.show()*/
            val check = LoginValidator().checkLogin(
                binding.textEmail.text.toString(),
                binding.textPassword.text.toString()
            )
            if (check) {
                var intent = Intent(
                    this,
                    PrincipalActivity::class.java
                )
                intent.putExtra(
                    "var1",
                    binding.textPassword.text.toString()
                ) //se pasa el nombre de la variable y valor
                intent.putExtra("var2", 11)
                startActivity(intent)
                //
            } else {
                Snackbar.make(
                    binding.textView,
                    "Usuario o contraseña invalidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    }

}