package com.flores.aplicacionmoviles.logic.marvelLogic

import com.example.aplicacionmovil.data.entities.marvel.characters.getMarvelChar
import com.flores.aplicacionmoviles.data.converters.ApiConnection
import com.flores.aplicacionmoviles.data.endPoints.MarvelEndpoint
import com.flores.aplicacionmoviles.logic.data.MarvelChars

class MarvelCharactersLogic {

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
}