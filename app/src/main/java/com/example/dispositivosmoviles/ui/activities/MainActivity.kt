package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator

//ESTA ES LA CAPA DE PRESENTACION
class MainActivity : AppCompatActivity() {

    //variable que se inicializara despues
    //Se tiene que poner el binding del activity en la que esta
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
        binding.btLogin.setOnClickListener {

            val check=LoginValidator().checkLogin(binding.txtName.text.toString(), binding.txtPass.text.toString())

            if(check){
                var intent= Intent(this, Activity2::class.java)
                intent.putExtra("var1"
                    ,"")
                startActivity(intent)
                intent.putExtra("var2"
                    ,2)
                startActivity(intent)
            }else{
                Snackbar.make(
                    binding.textView2,"Usuario o contraseña invalido", Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun initService(){

    }
}