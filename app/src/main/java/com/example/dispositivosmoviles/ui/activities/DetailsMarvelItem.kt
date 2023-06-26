package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.ActivityDetailsMarvelItemBinding
import com.example.dispositivosmoviles.databinding.MarvelChractersBinding
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {

    private lateinit var bindig :ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(bindig.root)
    }

    override fun onStart() {
        super.onStart()
        var name :String? = ""
        intent.extras?.let{
            name=it.getString("name")
        }/*
        if(!name.isNullOrEmpty()){
            bindig.nameCharDetail.text=name
        }*/
        val item =intent.getParcelableExtra<MarvelChars>("name")
        if(item!=null){
            bindig.nameCharDetail.text=item.name
            Picasso.get().load(item.image).into(bindig.imgCharDeta)
        }
    }

}