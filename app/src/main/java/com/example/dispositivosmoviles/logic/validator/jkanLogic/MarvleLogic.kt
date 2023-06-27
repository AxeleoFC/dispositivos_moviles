package com.example.dispositivosmoviles.logic.validator.jkanLogic

import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.JikanEndpoint
import com.example.dispositivosmoviles.data.endpoints.MarvelCharEndPoint
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars

class MarvleLogic {

    suspend fun getMarvelChars(name:String, limit:Int) : ArrayList<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        val response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelCharEndPoint::class.java)
            .getCharactersStarWhith(name,limit)

        if(response!=null){
            response.body()!!.data.results.forEach(){

                var comic:String =""
                if(it.comics.items.size>0){
                    comic=it.comics.items[0].name
                }

                val m = MarvelChars(
                    it.id,
                    it.name,
                    comic,
                    it.thumbnail.path+"."+it.thumbnail.extension
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}