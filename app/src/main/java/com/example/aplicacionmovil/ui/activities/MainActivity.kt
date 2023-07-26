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
import com.example.aplicacionmovil.ui.utilities.MyContextManager
import com.flores.aplicacionmoviles.logic.validator.LoginValidator
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
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

    private lateinit var client: SettingsClient
    private lateinit var locationSettingsRequest: LocationSettingsRequest

    private var currentLocation: Location? = null

    private val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(binding.correo, "", Snackbar.LENGTH_LONG)
            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    //Devuelve el texto de voz
                    val msg = activityResult
                        .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                        .toString()
                    //Para hacer una consulta con la voz
                    if (msg.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg)
                        startActivity(intent)
                    }
                    sn.setBackgroundTint(resources.getColor(R.color.blue))
                }
                RESULT_CANCELED -> {
                    message = "Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                }
                else -> {
                    message = "Ocurrio un error"
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                }
            }
            sn.setText(message)
            sn.show()
        }

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            when (isGranted) {
                true -> {

                    /*val alert = AlertDialog.Builder(this).apply {
                        setTitle("Notificacion")
                        setMessage("por favor verifique si el GPS esta encendido.")
                        setPositiveButton("Verificar"){dialog, id->
                            val i= Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivity(i)
                            dialog.dismiss()
                        }
                        setCancelable(false)
                    }.show()*/
                    client.checkLocationSettings(locationSettingsRequest).apply{
                        addOnSuccessListener {
                            val task = fusedLocationProviderClient.lastLocation
                            task.addOnSuccessListener { location ->
                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallback,
                                    Looper.getMainLooper()
                                )
                            }
                        }
                        addOnFailureListener {ex->
                            if(ex is ResolvableApiException){
                                ex.startResolutionForResult(this@MainActivity
                                    , LocationSettingsStatusCodes.RESOLUTION_REQUIRED)
                            }

                        }
                    }
                }

                shouldShowRequestPermissionRationale(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) -> {
                    Snackbar.make(
                        binding.correo,
                        "Ayude con el permiso no sea mlaito",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                false -> {
                    Snackbar.make(
                        binding.correo,
                        "DENEGADO",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000,
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (locationResult != null){
                    locationResult.locations.forEach{
                        currentLocation = it
                        Log.d("UCE",
                            "Ubicaión: ${it.latitude}, "+ "${it.longitude}")
                    }
                }
            }
        }

        client = LocationServices.getSettingsClient(this)
        locationSettingsRequest=LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).build()

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

    @SuppressLint("ResourceAsColor", "MissingPermission")
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
            locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
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
            val intentSpeech=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "DI ALGO...")
            speechToText.launch(intentSpeech)
        }
    }
    private suspend fun saveDataStore(stringData: String){
        dataStore.edit { prefs->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = UUID.randomUUID().toString()
        }
    }

    private fun test(){
        var location=MyContextManager(this)
        location.getClientLocation()
    }

}