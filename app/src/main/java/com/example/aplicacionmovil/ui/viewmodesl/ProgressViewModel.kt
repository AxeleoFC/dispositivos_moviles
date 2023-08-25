package com.example.aplicacionmovil.ui.viewmodesl

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovil.logic.marvelLogic.MarvelLogicDB
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel : ViewModel(){
    
    val progressState = MutableLiveData<Int>()
    val items = MutableLiveData<List<MarvelChars>>()

    fun processBackground(time: Long){
        viewModelScope.launch(Dispatchers.Main){
            val state1 = View.VISIBLE
            progressState.postValue(state1)
            delay(time*1000)
            val state2 = View.GONE
            progressState.postValue(state2)
        }
    }
    fun sumBackground(){
        viewModelScope.launch(Dispatchers.Main){
            val state1 = View.VISIBLE
            progressState.postValue(state1)
            var total=0
            for( i in 1..300){
                total+=i
            }
            val state2 = View.GONE
            progressState.postValue(state2)
        }
    }
    suspend fun getMarvelChars(ini: Int, fin: Int){
        progressState.postValue(View.VISIBLE)
        val item=MarvelLogicDB().getAllMarvelChars(ini,fin)
        items.postValue(item)
        progressState.postValue(View.GONE)
    }
}