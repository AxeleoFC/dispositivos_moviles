package com.example.aplicacionmovil.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.ActivitySecondBinding
import com.example.aplicacionmovil.ui.fragments.FirstFragment
import com.example.aplicacionmovil.ui.fragments.SecondFragment
import com.example.aplicacionmovil.ui.fragments.ThirdFragment

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
        var name: String = ""


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicioMenu -> {
                    com.flores.aplicacionmoviles.ui.utilities.FragmentsManager().replaceFragmet(supportFragmentManager, binding.frmContainer.id, SecondFragment())
                    true
                }

                R.id.buscarMenu -> {
                    com.flores.aplicacionmoviles.ui.utilities.FragmentsManager().replaceFragmet(supportFragmentManager, binding.frmContainer.id, FirstFragment())
                    true
                }

                R.id.apis -> {

                    com.flores.aplicacionmoviles.ui.utilities.FragmentsManager().replaceFragmet(supportFragmentManager, binding.frmContainer.id, ThirdFragment())
                    true
                }

                else -> false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}