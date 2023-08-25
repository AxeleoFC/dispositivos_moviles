package com.example.aplicacionmovil.data.entities.waifu

import com.example.aplicacionmovil.logic.data.WaifuChar

data class WaifuCharApi(
    val _id: Int,
    val from: From,
    val images: List<String>,
    val names: Names,
    val statistics: Statistics
)
fun WaifuCharApi.getWaifuChar(): WaifuChar {
    return WaifuChar(
        _id,from.name,images[0],names.en,statistics.love,""
    )
}