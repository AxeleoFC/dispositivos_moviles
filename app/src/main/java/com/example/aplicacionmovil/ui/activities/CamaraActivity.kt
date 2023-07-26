package com.example.aplicacionmovil.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.aplicacionmovil.databinding.ActivityCamaraBinding

class CamaraActivity : AppCompatActivity() {

    lateinit var binding: ActivityCamaraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCamaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCapturar.setOnClickListener {
            val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            camaraResult.launch(intent)
        }
        binding.imagenActivity.setOnClickListener {
            val compartirIntent=Intent(Intent.ACTION_SEND)
            compartirIntent.putExtra(Intent.EXTRA_TEXT, "Se quiere compartir")
            compartirIntent.setType("text/plain")
            startActivity(Intent.createChooser(compartirIntent, "Compartir"))
        }
    }

    private val camaraResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        when(result.resultCode){
            Activity.RESULT_OK->{
                val imagen=result.data?.extras?.get("data") as Bitmap
                binding.imagenActivity.setImageBitmap(imagen)
            }
            Activity.RESULT_CANCELED->{

            }
        }
    }
}