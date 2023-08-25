package com.example.aplicacionmovil.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.aplicacionmovil.databinding.ActivityProgressBinding
import com.example.aplicacionmovil.ui.viewmodesl.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    private val progressviewmodel by viewModels<ProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressviewmodel.progressState.observe(this
        ) { binding.progressBar.visibility = it }

        progressviewmodel.items.observe(this) {
            Toast.makeText(
                this,
                it[0].name,
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnProceso1.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                progressviewmodel.getMarvelChars(1,99)
            }
        }

        binding.btnProceso2.setOnClickListener {
            progressviewmodel.processBackground(6)
        }
    }
}