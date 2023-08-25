package com.flores.aplicacionmoviles.logic.jikanLogic

import com.flores.aplicacionmoviles.data.converters.ApiConnection
import com.flores.aplicacionmoviles.data.endPoints.JikanEndpoint
import com.flores.aplicacionmoviles.logic.data.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes(): ArrayList<MarvelChars> {
        var itemList = arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndpoint::class.java
        ).getAllAnimes() //endpoint base al especifico

        if (response.isSuccessful) {
            response.body()!!.data.forEach {
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url,
                    ""
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}