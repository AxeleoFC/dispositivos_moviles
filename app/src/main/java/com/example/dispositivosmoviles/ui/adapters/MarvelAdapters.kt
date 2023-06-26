package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.MarvelChractersBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


class MarvelAdapters(private val items: List<MarvelChars>, private var fnnClick:(MarvelChars)->Unit):
    //Funcion que no devuelve nada "Unit"
    RecyclerView.Adapter<MarvelAdapters.MarvelViewHolder>() {
    class MarvelViewHolder (view: View): RecyclerView.ViewHolder(view){

        private val binding: MarvelChractersBinding=MarvelChractersBinding.bind(view)

        fun render(item: MarvelChars, fnClick:(MarvelChars)->Unit){
            binding.textoName.text=item.name
            binding.textoComic.text=item.comic
            //Metodo picasso para cargar imagenes, se debe implementar en el gradle
            //para poder usrar
            Picasso.get().load(item.image).into(binding.imgChar)
            itemView.setOnClickListener{
                fnClick(item)
            //Snackbar.make(binding.imgChar, item.name, Snackbar.LENGTH_SHORT).show()
            }

        }
    }

    //Esto se hace solo una ves
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_chracters, parent, false))
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.render(items[position],fnnClick)
    }


}