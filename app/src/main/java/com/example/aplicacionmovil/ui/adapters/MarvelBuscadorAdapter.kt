package com.example.aplicacionmovil.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.MarvelBuscadorCharactersBinding
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.squareup.picasso.Picasso

class MarvelBuscadorAdapter() : RecyclerView.Adapter<MarvelBuscadorAdapter.MarvelViewHolder>() {

    var items: List<MarvelChars> = listOf()

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelBuscadorCharactersBinding =
            MarvelBuscadorCharactersBinding.bind(view)

        //    solo modificamos el render
        fun render(
            item: MarvelChars
        ) {
            binding.textName.text = item.name
            binding.textComic.text = item.comic
            Picasso.get().load(item.image).into(binding.imgMarvel)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelBuscadorAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_buscador_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelBuscadorAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<MarvelChars>) {
        this.items = this.items.plus(newItems) //agrega a la lista los nuevos elementos
        notifyDataSetChanged()
    }

    //en este metodo no se suman los items nuevos a los anteriores, se remplaza todo
    fun replaceListItems(newItems: List<MarvelChars>) {
        this.items = newItems //agrega a la lista los nuevos elementos
        notifyDataSetChanged()
    }

    fun replaceNoneList() {
        this.items = listOf()
    }


}