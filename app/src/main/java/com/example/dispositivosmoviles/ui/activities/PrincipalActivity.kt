package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.PrincipalActivityBinding
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.google.android.material.snackbar.Snackbar

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: PrincipalActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE", "Entrando a Create")
        binding = PrincipalActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()

        //opcion 1
//        intent.extras!!.let {
//            var name = it.getString("var1")
//        }

        //opcion 2
        var name: String = ""
        /*intent.extras.let {
            name = it?.getString("var1")!!
        }*/
        Log.d("UCE", "Hola ${name}")
        binding.txtName.text = "Bienvenido " + name.toString()
        Log.d("UCE", "Entrando a Start")
        initClass()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {

                    val frag = FirstFragment() //crea una instancia del fragment que se quiere agregar
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, frag) //se coloca el id del contenedor(framelayout) y se pasa la instancia del fragment que se quiere agregar
                    //con .add se inserta otro fragment(mas contenido encima del anterior) encima
                    transaction.addToBackStack(null)
                    transaction.commit() //guarda los cambios

                    true
                }

                R.id.favoritos -> {
                    var suma:Int = 0
                    for(i in listOf(8,12,13)){
                        suma +=i
                    }
                    Snackbar.make(binding.txtName,
                        "La suma es ${suma}",
                        Snackbar.LENGTH_LONG)
                        .show()
                    true
                }

                R.id.chatgpt -> {
                    // Respond to navigation item 2 click
                    true
                }

                else -> false
            }
        }

    }

    private fun initClass() {
        binding.botonRetorno.setOnClickListener {
            //el primer parametro es un filtro para que muestre solo eso en la consola al filtar por el termino y el segundo el mensaje
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}