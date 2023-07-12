package com.flores.aplicacionmoviles.data.endPoints

import com.example.aplicacionmovil.data.entities.jikan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndpoint {
    @GET("top/anime")
    suspend fun getAllAnimes() : Response<JikanAnimeEntity>
}