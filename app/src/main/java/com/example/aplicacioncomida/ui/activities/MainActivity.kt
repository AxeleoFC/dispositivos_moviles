package com.example.aplicacioncomida.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacioncomida.R
import com.example.aplicacioncomida.databinding.ActivityMainBinding
import com.example.aplicacioncomida.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {


    private lateinit var binding:ActivityMainBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.ingresarbutID.setOnClickListener {

            val check= LoginValidator().checkLogin(binding.editTextEmailID.text.toString(), binding.editTextTextPassword.text.toString())

            if(check){
                var intent= Intent(this, MainActivityInicio::class.java)
                intent.putExtra("var1"
                    ,"")
                startActivity(intent)
                intent.putExtra("var2"
                    ,2)
                startActivity(intent)
            }else{
                Snackbar.make(
                    binding.ingresarbutID,"Usuario o contraseña invalido", Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun initService(){

    }


}