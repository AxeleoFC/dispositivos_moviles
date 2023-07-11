package com.example.aplicacionmovil.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.ActivityDetailsMarvelItemBinding
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val item = intent.getParcelableExtra<MarvelChars>("name")

        if (item != null){
            binding.textoName.text = item.name
            Picasso.get().load(item.image).into(binding.imgChar)
            binding.textoComic.text = item.comic
        }
    }
}