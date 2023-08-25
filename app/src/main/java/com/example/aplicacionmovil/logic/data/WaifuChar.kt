package com.example.aplicacionmovil.logic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WaifuChar(val _id: Int,
                     val from: String,
                     val images: String,
                     val names: String,
                     val statistics: Int,
                     var user_w:String
) : Parcelable
