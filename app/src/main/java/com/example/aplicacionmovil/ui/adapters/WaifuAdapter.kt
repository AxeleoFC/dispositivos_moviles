package com.example.aplicacionmovil.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.MarvelCharactersBinding
import com.example.aplicacionmovil.logic.data.WaifuChar
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.flores.aplicacionmoviles.ui.adapters.MarvelAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class WaifuAdapter (private var user:String,
    private var fnClick: (WaifuChar) -> Unit
) :
    RecyclerView.Adapter<WaifuAdapter.WaifuViewHolder>() {

    var items: List<WaifuChar> = listOf()
    class WaifuViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)
        private var fireBase = Firebase.firestore

        fun render(item: WaifuChar, fnClick: (WaifuChar) -> Unit,user:String) {
            binding.textoName.text = item.names
            binding.textoComic.text = item.from
            binding.btnFavorito.setOnClickListener {
                insertarWaifu(item,user)
            }
            if (isValidImageUrl(item.images)) {
                Picasso.get().load(item.images).into(binding.imgChar)
            } else {
                binding.imgChar.setImageResource(R.drawable.notfound)
            }
            itemView.setOnClickListener {
                fnClick(item)
            }

        }
        private fun isValidImageUrl(url: String): Boolean {
            return !url.endsWith(".jpeg") && (url.startsWith("http") || url.startsWith("https")) && (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif") || url.endsWith("jpeg"))
        }
        fun insertarWaifu(waifu: WaifuChar, user: String) {
            try {
                val TAG = "Waifu"
                val waifuName = waifu.names
                fireBase.collection("waifus")
                    .whereEqualTo("names", waifuName)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (querySnapshot.isEmpty) {
                            waifu.user_w = user
                            fireBase.collection("waifus")
                                .add(waifu)
                                .addOnSuccessListener {
                                    Snackbar.make(binding.root, "Se agregó: ${waifuName}.", Snackbar.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error adding document", e)
                                }
                        } else {
                            Snackbar.make(binding.root, "El personaje: ${waifuName} ya esta guardado.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error checking for existing item", e)
                    }
            }catch (e:Exception){
                Snackbar.make(binding.root, "Se dio un error al intentar guardar.", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaifuAdapter.WaifuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WaifuViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WaifuAdapter.WaifuViewHolder, position: Int) {
        holder.render(items[position], fnClick, user)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<WaifuChar>){
        this.items = this.items.plus(newItems)
        notifyDataSetChanged()
    }

    fun replaceListItems(newItems: List<WaifuChar>){
        this.items = newItems //agrega a la lista los nuevos elementos
        notifyDataSetChanged()
    }

    fun updateData(newItems: List<WaifuChar>) {
        items = newItems
        notifyDataSetChanged()
    }

}