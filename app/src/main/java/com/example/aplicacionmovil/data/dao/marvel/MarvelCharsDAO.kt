package com.flores.aplicacionmoviles.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionmovil.data.entities.marvel.characters.database.MarvelCharsDB

@Dao
interface MarvelCharsDAO {
    @Query("select * from MarvelCharsDB")
    fun getAllCharacters(): List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id = :pk")
    fun getOneCharacter(pk: Int): MarvelCharsDB

    @Insert
    fun insertMarvelChar(ch: List<MarvelCharsDB>)

}