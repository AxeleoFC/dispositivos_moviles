package com.example.dispositivosmoviles.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispositivosmoviles.data.dao.marvel.MarvelCharsDao

@Database(
    entities = [MarvelConnectionDB::class],
    version = 1
)
abstract class MarvelConnectionDB: RoomDatabase() {

    abstract fun marvelDao():MarvelCharsDao
}