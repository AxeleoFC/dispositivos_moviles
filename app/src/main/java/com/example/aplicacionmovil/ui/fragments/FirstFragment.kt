package com.example.aplicacionmovil.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.FragmentFirstBinding
import com.example.aplicacionmovil.logic.marvelLogic.MarvelLogicDB
import com.example.aplicacionmovil.ui.activities.DetailsMarvelItem
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.flores.aplicacionmoviles.logic.jikanLogic.JikanAnimeLogic
import com.flores.aplicacionmoviles.logic.marvelLogic.MarvelCharactersLogic
import com.flores.aplicacionmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {


    private var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItem(it) }
    private lateinit var binding:FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager

    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(
            layoutInflater,
            container,
            false)


        lmanager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        gManager = GridLayoutManager(requireActivity(), 2)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        chargeDataRVDB()

        binding.rvSwipe2.setOnRefreshListener {
            chargeDataRVDB()
            binding.rvSwipe2.isRefreshing = false
        }


        binding.rvMarvelChars2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(
                    recyclerView,
                    dx,
                    dy
                ) //dy es para el scroll de abajo y dx es de izquierda a derech para buscar elementos
                val v = lmanager.childCount  //cuantos elementos han pasado
                val p = lmanager.findFirstVisibleItemPosition() //posicion actual
                val t = lmanager.itemCount //cuantos tengo en total
                if (dy > 0) {
                    //necesitamos comprobar si el total es mayor igual que los elementos que han pasado entonces ncesitamos actualizar ya que estamos al final de la lista
                    if ((v + p) >= t) {
                        chargeDataRVDB()
                        lifecycleScope.launch((Dispatchers.IO)) {
                            val newItems = MarvelCharactersLogic().getAllMarvelChars(0, 99)
                            withContext(Dispatchers.Main) {
                                rvAdapter.updateListItems(newItems)
                            }
                        }
                    }
                }
            }
        })

        binding.txtFilter2.addTextChangedListener { filteredText ->
            val newItems = marvelCharsItems.filter { items ->
                items.name.lowercase().contains(filteredText.toString().lowercase())
            }
            rvAdapter.replaceListItems(newItems)
        }

    }

    private fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    private fun chargeDataRVF() {
        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (JikanAnimeLogic().getAllAnimes())
                //MarvelCharactersLogic().getAllMarvelChars(
                //                    0,
                //                    99
                //                )
            }

            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars2.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }
        }
    }

    private fun chargeDataRVDB() {
        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharsItems = withContext(Dispatchers.IO) {
                var marvelChars= MarvelLogicDB().getAllMarvelCharsDB().toMutableList()
                if(marvelChars.isEmpty()){
                    marvelChars=(MarvelCharactersLogic().getAllMarvelChars(1,99))
                }

                return@withContext (marvelChars)
            }
            rvAdapter.items = marvelCharsItems
            binding.rvMarvelChars2.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }
        }
    }
}