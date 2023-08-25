package com.example.aplicacionmovil.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.FragmentFirstBinding
import com.example.aplicacionmovil.logic.data.WaifuChar
import com.example.aplicacionmovil.logic.marvelLogic.MarvelLogicDB
import com.example.aplicacionmovil.logic.waifuLogic.WaifuLogic
import com.example.aplicacionmovil.ui.activities.DetailsMarvelItem
import com.example.aplicacionmovil.ui.activities.dataStore
import com.example.aplicacionmovil.ui.adapters.WaifuAdapter
import com.example.aplicacionmovil.ui.data.UserDataStore
import com.example.aplicacionmovil.ui.utilities.Metodos
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.flores.aplicacionmoviles.logic.jikanLogic.JikanAnimeLogic
import com.flores.aplicacionmoviles.logic.marvelLogic.MarvelCharactersLogic
import com.flores.aplicacionmoviles.ui.adapters.MarvelAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {

    private lateinit var rvAdapter: WaifuAdapter
    private lateinit var binding:FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager
    private var limit = 5
    private var offset = 0
    private var wifuItems: MutableList<WaifuChar> = mutableListOf<WaifuChar>()
    private var isLoading = false

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
        val user = arguments?.getString("user")
        rvAdapter= WaifuAdapter(user!!) { sendWaifuItem(it) }
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.layaoutSimmer.visibility=View.VISIBLE
        binding.linearLayout2.visibility=View.GONE
        chargeDataRVInit(offset,limit){
            binding.layaoutSimmer.visibility=View.GONE
            binding.linearLayout2.visibility=View.VISIBLE
        }
        binding.rvSwipe2.setOnRefreshListener {
            chargeDataRV(offset,limit)
            binding.rvSwipe2.isRefreshing = false
        }


        binding.rvMarvelChars2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = gManager.itemCount
                val firstVisibleItemPosition = gManager.findFirstVisibleItemPosition()
                val middlePosition = totalItemCount / 2
                if (!isLoading && firstVisibleItemPosition >= middlePosition) {
                    isLoading = true
                    CargandoDatos()
                }
            }
        })

        binding.txtFilter2.addTextChangedListener { filteredText ->
            val newItems = wifuItems.filter { items ->
                items.names.lowercase().contains(filteredText.toString().lowercase())
            }
            lifecycleScope.launch(Dispatchers.Main){
                binding.textoFS1.text=filteredText.toString()
            }
            rvAdapter.replaceListItems(newItems)
        }

    }
    private fun CargandoDatos() {
        Snackbar.make(binding.root, "Cargando nuevas waifus...", Snackbar.LENGTH_SHORT).show()
        lifecycleScope.launch(Dispatchers.Main) {
            val newItems = withContext(Dispatchers.IO) {
                val fetchedIds = wifuItems.map { it._id }
                val freshItems = WaifuLogic().getWaifusChar(limit)
                    .filter { it._id !in fetchedIds }
                freshItems
            }
            rvAdapter.updateListItems(newItems)
            wifuItems.addAll(newItems)
            offset += limit
            isLoading = false
        }
    }


    private fun sendWaifuItem(item: WaifuChar) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun chargeDataRV(limit: Int,offset: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            wifuItems = withContext(Dispatchers.IO) {
                return@withContext (WaifuLogic().getWaifusChar(limit))
            }
            rvAdapter.items = wifuItems

            binding.rvMarvelChars2.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }
            this@FirstFragment.offset += limit
        }
    }

    fun chargeDataRVInit(offset: Int, limit: Int, callback: () -> Unit)  {
        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                wifuItems = withContext(Dispatchers.IO) {
                    return@withContext (WaifuLogic().getWaifusChar(limit))
                }
                rvAdapter.items = wifuItems
                binding.rvMarvelChars2.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gManager
                }
                this@FirstFragment.offset += limit
                callback()
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

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                //marvelCharsItemsDB=MarvelLogicDB().getAllMarvelCharsDB().toMutableList()
            }

        }
    }
}