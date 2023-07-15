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
import com.example.aplicacionmovil.ui.utilities.Metodos
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.flores.aplicacionmoviles.logic.jikanLogic.JikanAnimeLogic
import com.flores.aplicacionmoviles.logic.marvelLogic.MarvelCharactersLogic
import com.flores.aplicacionmoviles.ui.adapters.MarvelAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {


    private var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItem(it) }
    private lateinit var binding:FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager
    private var limit = 99
    private var offset = 0

    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private var marvelCharsItemsDB: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

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

        chargeDataRVInit(offset,limit)
        binding.rvSwipe2.setOnRefreshListener {
            chargeDataRV(offset,limit)
            binding.rvSwipe2.isRefreshing = false
        }


        binding.rvMarvelChars2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(
                    recyclerView,
                    dx,
                    dy
                )
                val v = gManager.childCount  //cuantos elementos han pasado
                val p = gManager.findFirstVisibleItemPosition() //posicion actual
                val t = gManager.itemCount //cuantos tengo en total
                if (dy > 0) {
                    if ((v + p) >= t) {
                        updateDataRV(limit,offset)
                        var newItems= listOf<MarvelChars>()

                        lifecycleScope.launch((Dispatchers.Main)) {
                            this@FirstFragment.offset += limit
                            newItems=withContext(Dispatchers.IO) {
                                return@withContext (MarvelCharactersLogic().getAllMarvelChars(offset, limit))
                            }
                            rvAdapter.updateListItems(newItems)
                            if(offset==56430){
                                offset=0
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

    fun chargeDataRV(limit: Int,offset: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelCharactersLogic().getAllMarvelChars(offset, limit))
            }
            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars2.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }
            this@FirstFragment.offset += limit
        }
    }

    private fun chargeDataRVDB(limit: Int, offset: Int) {
        if(Metodos().isOnline(requireActivity())){
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharsItems = withContext(Dispatchers.IO) {
                    var marvelChars= MarvelLogicDB().getAllMarvelCharsDB().toMutableList()
                    if(marvelChars.isEmpty()){
                        marvelChars=(MarvelCharactersLogic().getAllMarvelChars(offset,limit))
                    }

                    return@withContext (marvelChars)
                }
                rvAdapter.items = marvelCharsItems
                binding.rvMarvelChars2.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gManager
                }
            }
            this@FirstFragment.offset +=limit
            this@FirstFragment.limit +=20
        }else{
            Snackbar.make(binding.cardView2,"No se pudo cargar",Snackbar.LENGTH_LONG).show()
        }

    }


    fun chargeDataRVInit(offset: Int,limit: Int) {
        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharsItems = withContext(Dispatchers.IO) {
                    return@withContext (MarvelCharactersLogic().getAllMarvelChars(offset, limit))
                }
                rvAdapter.items = marvelCharsItems
                binding.rvMarvelChars2.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gManager
                }
                this@FirstFragment.offset += limit
            }
        } else {
            Snackbar.make(
                binding.cardView2,
                "No hay conexion",
                Snackbar.LENGTH_LONG
            )
                .show()
        }
    }

    fun updateDataRV(limit: Int,offset: Int) {
        var items:List<MarvelChars> = listOf()
        lifecycleScope.launch(Dispatchers.Main) {
            items = withContext(Dispatchers.IO) {
                return@withContext (MarvelCharactersLogic().getAllMarvelChars(offset, limit))
            }
            rvAdapter.updateListItems(items)
            binding.rvMarvelChars2.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }
            this@FirstFragment.offset += limit
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                marvelCharsItemsDB=MarvelLogicDB().getAllMarvelCharsDB().toMutableList()
            }

        }
    }
}