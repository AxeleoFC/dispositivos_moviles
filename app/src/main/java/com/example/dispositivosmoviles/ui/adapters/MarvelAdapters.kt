package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.MarvelChractersBinding


class MarvelAdapters(private  val items: List<MarvelChars>):
    RecyclerView.Adapter<MarvelAdapters.MarvelViewHolder>() {
    class MarvelViewHolder (view: View): RecyclerView.ViewHolder(view){

        private val binding: MarvelChractersBinding=MarvelChractersBinding.bind(view)

        fun render(item: MarvelChars){
            binding.textoName.text=item.name
            binding.textoComic.text=item.comic
        }
    }

    //Esto se hace solo una ves
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_chracters, parent, false))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.render(items[position])
    }


}