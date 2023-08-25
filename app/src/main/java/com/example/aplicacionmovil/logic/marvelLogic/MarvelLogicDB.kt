package com.example.aplicacionmovil.logic.marvelLogic

import com.example.aplicacionmovil.data.entities.marvel.characters.database.MarvelCharsDB
import com.example.aplicacionmovil.data.entities.marvel.characters.getMarvelChar
import com.example.aplicacionmovil.ui.utilities.AplicacionMovil
import com.flores.aplicacionmoviles.data.converters.ApiConnection
import com.flores.aplicacionmoviles.data.endPoints.MarvelEndpoint
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.flores.aplicacionmoviles.logic.data.getMarvelsCharacters

class MarvelLogicDB {

    suspend fun getMarvelChars(name: String, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        ).getCharactersStartWith(name, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChar()
                itemList.add(m)
            }
        }
        return itemList
    }

    suspend fun getAllMarvelChars(offset: Int, limit: Int): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        ).getAllMarvelChars(offset, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChar()
                itemList.add(m)
            }
        }
        return itemList
    }

    suspend fun getAllMarvelCharsDB(): List<MarvelChars>{
        val items:ArrayList<MarvelChars> = arrayListOf()
        val itemsAux=AplicacionMovil.getDbInstance().marvelDao().getAllCharacters()
        itemsAux.forEach {
            items.add(MarvelChars(it.id,it.name,it.comic,it.synapsi,it.image))
        }
        return items
    }

    suspend fun insertMarvelCharactersDB(items: List<MarvelChars>):List<MarvelCharsDB>{
        var itemsDB= arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelsCharacters())
        }
        return itemsDB
    }
}