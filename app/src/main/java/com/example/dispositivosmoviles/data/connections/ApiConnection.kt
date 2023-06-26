package com.example.dispositivosmoviles.data.connections
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiConnection {

    fun getJkanConnection(): Retrofit{
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        // var service: GitHubService = retrofit.create(GitHubService::class.java)

        return retrofit
    }
}