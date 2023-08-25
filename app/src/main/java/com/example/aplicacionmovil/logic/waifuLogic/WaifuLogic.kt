package com.example.aplicacionmovil.logic.waifuLogic

import com.example.aplicacionmovil.data.endPoints.waifuEnd
import com.example.aplicacionmovil.data.entities.marvel.characters.getMarvelChar
import com.example.aplicacionmovil.data.entities.waifu.WaifuCharApi
import com.example.aplicacionmovil.data.entities.waifu.getWaifuChar
import com.example.aplicacionmovil.logic.data.WaifuChar
import com.flores.aplicacionmoviles.data.converters.ApiConnection
import com.flores.aplicacionmoviles.data.endPoints.MarvelEndpoint
import com.flores.aplicacionmoviles.logic.data.MarvelChars

class WaifuLogic {

    suspend fun getWaifusChar(limit: Int): ArrayList<WaifuChar> {
        val itemList = ArrayList<WaifuChar>()
        val encounteredIds = HashSet<String>()
        while (itemList.size < limit) {
            val waifu = waifuEnd().conseguirWaifu()
            if (waifu != null) {
                val waifuId = waifu._id
                if (!encounteredIds.contains(waifuId.toString())) {
                    val waifuChar = waifu.getWaifuChar()
                    itemList.add(waifuChar)
                    encounteredIds.add(waifuId.toString())
                }
            }
        }
        return itemList
    }

}