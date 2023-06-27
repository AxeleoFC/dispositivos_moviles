package com.example.dispositivosmoviles.logic.validator.jkanLogic

import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.JikanEndpoint
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes() : List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndpoint::class.java).getAllAnimes()

        if(response!=null){
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