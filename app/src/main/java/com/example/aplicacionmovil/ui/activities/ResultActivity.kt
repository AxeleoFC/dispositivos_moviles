package com.example.aplicacionmovil.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val i= Intent()
        binding.ok.setOnClickListener {
            i.putExtra("result", "Resultdao exitoso")
            setResult(RESULT_OK, i)
            finish()
        }
        binding.btfalso.setOnClickListener {
            i.putExtra("result", "Resultdao fallido")
            setResult(RESULT_CANCELED, i)
            finish()
        }

    }
}