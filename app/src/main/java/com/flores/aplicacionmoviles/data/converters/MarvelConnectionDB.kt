package com.flores.aplicacionmoviles.data.converters

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aplicacionmovil.data.entities.marvel.characters.database.MarvelCharsDB
import com.flores.aplicacionmoviles.data.dao.marvel.MarvelCharsDAO


@Database(
    entities = [MarvelCharsDB::class],
    version = 1
)
abstract class MarvelConnectionDB : RoomDatabase() {

    abstract fun marvelDao(): MarvelCharsDAO

}