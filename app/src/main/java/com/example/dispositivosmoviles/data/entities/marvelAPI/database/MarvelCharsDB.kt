package com.example.dispositivosmoviles.data.entities.marvelAPI.database

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MarvelCharsDB(val id:Int, val name:String, val comic:String, val img: String):Parcelable
