package com.example.aplicacionmovil.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplicacionmovil.R
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Probar : AppCompatActivity() {
    companion object {
        const val TAG = "Comprobar"
        const val url = "https://waifu.it/api/waifu"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = fetchData()
                val waifuData = JSONObject(response)
                val imagesArray = waifuData.getJSONArray("images")
                val firstImage = imagesArray.getString(0)
                Log.d(TAG, "Waifu Data: $waifuData")
                Log.d(TAG, "Waifu Data: $imagesArray")
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }

    private fun fetchData(): String {
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
}