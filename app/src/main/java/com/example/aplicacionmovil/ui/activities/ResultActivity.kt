package com.example.aplicacionmovil.ui.activities

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
        binding.ok.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
        binding.btfalso.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }
}