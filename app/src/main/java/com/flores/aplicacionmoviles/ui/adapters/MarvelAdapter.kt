package com.flores.aplicacionmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flores.aplicacionmoviles.databinding.MarvelCharactersBinding
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.squareup.picasso.Picasso

class MarvelAdapter (
    private var fnClick: (MarvelChars) -> Unit
) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    var items: List<MarvelChars> = listOf()
    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        //    solo modificamos el render
        fun render(
            item: MarvelChars,
            fnClick: (MarvelChars) -> Unit
        ) {
            binding.textName.text = item.name
            binding.textComic.text = item.comic
            Picasso.get().load(item.image).into(binding.imgMarvel)

            itemView.setOnClickListener {
                fnClick(item)
//            Snackbar.make(binding.imgMarvel, item.name, Snackbar.LENGTH_SHORT).show()
//            de una clase no se puede ir a un activity solo se puede de activity a otra activity
//            basicamente no se puede hacer: val intent =  Intent()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<MarvelChars>){
        this.items = this.items.plus(newItems) //agrega a la lista los nuevos elementos
        notifyDataSetChanged()
    }

    //en este metodo no se suman los items nuevos a los anteriores, se remplaza todo
    fun replaceListItems(newItems: List<MarvelChars>){
        this.items = newItems //agrega a la lista los nuevos elementos
        notifyDataSetChanged()
    }
}