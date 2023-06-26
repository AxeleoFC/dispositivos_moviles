package com.example.dispositivosmoviles.logic.validator.jkanLogic

import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpints.JikanEndpoint
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes() : List<MarvelChars>{

        var call = ApiConnection.getJkanConnection()

        val response = call.create(JikanEndpoint::class.java).getAllAnimes()

        var itemList = arrayListOf<MarvelChars>()
        if(response.isSuccessful){
            response.body()!!.data.forEach{
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url
                )
                itemList.add(m)
            }

        }
        return itemList
    }
}