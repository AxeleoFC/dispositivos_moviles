package com.example.aplicacionmovil.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.aplicacionmovil.data.entities.marvel.characters.database.MarvelCharsDB
import com.flores.aplicacionmoviles.data.converters.MarvelConnectionDB
import com.flores.aplicacionmoviles.logic.data.MarvelChars

class AplicacionMovil : Application(){

    override fun onCreate() {
        super.onCreate()
        db=Room.databaseBuilder(applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB").build()
    }
    companion object{
        var db: MarvelConnectionDB? =null
        fun getDbInstance():MarvelConnectionDB{
            return db!!
        }
    }
}