package com.example.aplicacionmovil.data.endPoints

import android.util.Log
import com.example.aplicacionmovil.data.entities.waifu.From
import com.example.aplicacionmovil.data.entities.waifu.Names
import com.example.aplicacionmovil.data.entities.waifu.Statistics
import com.example.aplicacionmovil.data.entities.waifu.WaifuCharApi
import com.example.aplicacionmovil.ui.activities.Probar
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class waifuEnd {

    private fun retornarDatos(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://waifu.it/api/waifu")
            .get()
            .addHeader("Authorization", "NjU1NTgwODEyODcxMjA0ODc2.MTY5MjczOTU5MQ--.f5d3b7aa4b")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            return response.body()?.string() ?: throw IOException("Response body is null")
        }
    }
    fun conseguirWaifu(): WaifuCharApi? {
        try {
            //GlobalScope.launch(Dispatchers.IO)
            val waifuData = JSONObject(retornarDatos())
            val imagesArray = waifuData.getJSONArray("images")
            val imagesList = mutableListOf<String>()
            for (i in 0 until imagesArray.length()) {
                val imageUrl = imagesArray.getString(i)
                imagesList.add(imageUrl)
            }
            val namesObject = waifuData.getJSONObject("names")
            val fromObject = waifuData.getJSONObject("from")
            val statisticsObject = waifuData.getJSONObject("statistics")

            val waifu=WaifuCharApi(waifuData.getInt("_id")
                , From(fromObject.getString("name"),fromObject.getString("type"))
                ,imagesList
                , Names(namesObject.getString("alt"),namesObject.getString("en"),namesObject.getString("jp"))
                , Statistics(statisticsObject.getInt("downvote")
                    ,statisticsObject.getInt("fav")
                    ,statisticsObject.getInt("hate")
                    ,statisticsObject.getInt("love")
                    ,statisticsObject.getInt("upvote"))
            )
            Log.d(Probar.TAG, "Valio waifuEnd: ${waifu._id}")
            return waifu
        } catch (e: Exception) {
            Log.e(Probar.TAG, "Error en waifuEnd: ${e.message}")
            return null
        }
    }
}