package com.example.aplicacionmovil.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.ActivityMainBinding
import com.flores.aplicacionmoviles.logic.validator.LoginValidator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var currentLocation:Location?=null

    private val speechToText=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultActivity->
        var rColor=R.color.black
        var mensaje= ""
        when(resultActivity.resultCode){
            RESULT_OK->{
                rColor=R.color.blue
                val msg=resultActivity.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                    .toString()
                mensaje=msg
                if(msg.isNotEmpty()){
                    val intent=Intent(Intent.ACTION_WEB_SEARCH)
                    intent.setClassName("com.google.android.googlequicksearchbox",
                        "com.google.android.googlequicksearchbox.SearchActivity")
                    //poner lo que se quiere buscar
                    intent.putExtra(SearchManager.QUERY,msg)
                    startActivity(intent)
                }
            }
            RESULT_CANCELED->{
                rColor=R.color.rojo
                mensaje="Proceso cancelado"
            }else->{
            mensaje="Ocurrio un error"
        }
        }
        val sn=Snackbar.make(
            binding.pedOnl, mensaje,Snackbar.LENGTH_LONG
        )
        val color=resources.getColor(rColor)
        sn.setBackgroundTint(color)
        sn.show()
    }

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGrant->
            when(isGrant){
                true->{
                    val task=fusedLocationProviderClient.lastLocation
                    task.addOnSuccessListener {location->
                        val alert=AlertDialog.Builder(this)
                        alert.apply {
                            setTitle("Alerta")
                            setMessage("Existe un problema con el sistema o el funcionamineto")
                            setPositiveButton("OK"){dialog, id->
                                dialog.dismiss()
                            }
                            setNegativeButton("Cancelar"){dialog, id->
                                dialog.dismiss()
                            }
                            setCancelable(false)
                        }.create()
                        alert.show()
                        currentLocation=location
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper())
                    }
                    task.addOnFailureListener{

                    }
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                ->{
                    Snackbar.make(binding.correo,"Se denego el permiso",Snackbar.LENGTH_LONG).show()
                }
                false->{
                    Snackbar.make(binding.correo,"Acceso denegado",Snackbar.LENGTH_LONG).show()
                }
                else->{
                    Snackbar.make(binding.correo,"Se dio un error",Snackbar.LENGTH_LONG).show()
                }
            }

        }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        locationRequest=LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000,
        ).setMaxUpdates(3)
            .build()
        locationCallback = object :LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                if(locationResult!=null){
                    locationResult.locations.forEach{location->
                        currentLocation=location
                        Log.d("UCE","Ubicacion: ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
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

            )
            val intent=Intent(Intent.ACTION_WEB_SEARCH)
            intent.setClassName("com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity")
            //poner lo que se quiere buscar
            intent.putExtra(SearchManager.QUERY,"Elden Ring")
            startActivity(intent)*/
            //---------------------------------------------------------------------------
            locationContract.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            //locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

        }

        val appResultLocal = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultActivity->
            var rColor=R.color.black
            var mensaje=when(resultActivity.resultCode){
                RESULT_OK->{
                    rColor=R.color.blue
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }
                RESULT_CANCELED->{
                    rColor=R.color.rojo
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }else->{
                    "Resultado dudoso"
                }
            }
            val sn=Snackbar.make(
                binding.pedOnl, mensaje,Snackbar.LENGTH_LONG
            )
            val color=resources.getColor(rColor)
            sn.setBackgroundTint(color)
            sn.show()
        }



        binding.facebook.setOnClickListener {
            val intenSpeech=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intenSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intenSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                Locale.getDefault()
            )
            intenSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                "Di algo"
            )
            speechToText.launch(intenSpeech)
        /*val resIntent=Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)*/
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