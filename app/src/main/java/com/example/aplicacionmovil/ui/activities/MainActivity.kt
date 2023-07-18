package com.example.aplicacionmovil.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.ActivityMainBinding
import com.flores.aplicacionmoviles.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("WrongViewCast")
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
        binding.ingresar.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.ingresoCorreo.text.toString(),
                binding.ingresoContrasena.text.toString()
            )

            if (check) {
                lifecycleScope.launch(Dispatchers.IO){
                    saveDataStore(binding.ingresoCorreo.text.toString())
                }

                var intent = Intent(
                    this,
                    SecondActivity::class.java
                )

                intent.putExtra(
                    "var1",
                    ""
                )

                intent.putExtra("var2", 2)
                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.pedOnl, "Usuario o contraseña inválidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        binding.twitter.setOnClickListener {
            /*Forma de mandar a otra direccion o aplicacion
            val intent=Intent(Intent.ACTION_VIEW,
                //Uri.parse("geo: -0.2324234,-23.423423490")
                        Uri.parse("tel:0123456789")
                //Uri.parse("http://google.com.ec")

            )*/
            val intent=Intent(Intent.ACTION_WEB_SEARCH)
            intent.setClassName("com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity")
            //poner lo que se quiere buscar
            intent.putExtra(SearchManager.QUERY,"Elden Ring")
            startActivity(intent)
        }

        val appResultLocal = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultActivity->
            when(resultActivity.resultCode){
                RESULT_OK->{
                    Snackbar.make(
                        binding.pedOnl, "Resultado exitoso",Snackbar.LENGTH_LONG
                    )
                }
                RESULT_CANCELED->{
                    Snackbar.make(
                        binding.pedOnl, "Resultado fallido",Snackbar.LENGTH_LONG
                    )
                }else->{
                Snackbar.make(
                    binding.pedOnl, "Resultado dudoso",Snackbar.LENGTH_LONG
                )
                }
            }
        }

        binding.facebook.setOnClickListener {
            val resIntent=Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }
    }
    private suspend fun saveDataStore(stringData: String){
        dataStore.edit { prefs->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = UUID.randomUUID().toString()
        }
    }

}