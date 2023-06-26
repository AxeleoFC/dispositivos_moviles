package com.example.dispositivosmoviles.data.endpints

import com.example.dispositivosmoviles.data.entities.jkan.JikanAnimeEntity
import retrofit2.Response

import retrofit2.http.GET

interface JikanEndpoint  {
    @GET("top/anime")
    suspend fun getAllAnimes(): Response<JikanAnimeEntity>


}