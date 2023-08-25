package com.example.aplicacionmovil.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.ActivitySecondBinding
import com.example.aplicacionmovil.ui.fragments.FirstFragment
import com.example.aplicacionmovil.ui.fragments.SecondFragment
import com.example.aplicacionmovil.ui.fragments.ThirdFragment
import com.flores.aplicacionmoviles.ui.utilities.FragmentsManager
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        android.util.Log.d("UCE", "Entrando a Create")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        android.util.Log.d("UCE", "Entrando a Start")
        initClass()
    }

    private fun initClass() {
        val user = intent.getStringExtra("user")


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val bundle = Bundle()
            bundle.putString("user", user)
            when (item.itemId) {
                R.id.inicioMenu -> {
                    val secondFragment = SecondFragment()
                    secondFragment.arguments = bundle
                    FragmentsManager().replaceFragmet(supportFragmentManager, binding.frmContainer.id, secondFragment)
                    true
                }

                R.id.buscarMenu -> {
                    val firstFragment = FirstFragment()
                    firstFragment.arguments = bundle
                    FragmentsManager().replaceFragmet(supportFragmentManager, binding.frmContainer.id, firstFragment)
                    true
                }

                R.id.apis -> {
                    autenticacionBiometrica(bundle)

                    true
                }

                else -> false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun autenticacionBiometrica(bundle:Bundle){
        if(checkBiometric()){
            val executor= ContextCompat.getMainExecutor(this)
            val biometricPrompt = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion requerida")
                .setSubtitle("Ingrese su huella digital")
                .setNegativeButtonText("Cancelar")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build()
            val biometricManager = BiometricPrompt(this
                , executor
                , object : BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                    }
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        val thirdFragment = ThirdFragment()
                        thirdFragment.arguments = bundle
                        FragmentsManager().replaceFragmet(supportFragmentManager, binding.frmContainer.id, thirdFragment)
                    }
                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                })
            biometricManager.authenticate(biometricPrompt)
        }else{
            Snackbar.make(binding.root, "No se tiene los requisitos para hacer esto", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun checkBiometric():Boolean{
        var comprobar: Boolean=false
        val biometricManager= BiometricManager.from(this)
        when(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)){
            BiometricManager.BIOMETRIC_SUCCESS->{
                comprobar = true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                comprobar= false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                comprobar= false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->{
                val intent= Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                intent.putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED
                    , BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                )

                startActivity(intent)
                comprobar= true
            }
        }
        return comprobar
    }
}