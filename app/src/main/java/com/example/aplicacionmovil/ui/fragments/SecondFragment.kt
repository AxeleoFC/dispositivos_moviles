package com.example.aplicacionmovil.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionmovil.databinding.FragmentSecondBinding
import com.example.aplicacionmovil.logic.data.WaifuChar
import com.example.aplicacionmovil.logic.waifuLogic.WaifuLogic
import com.example.aplicacionmovil.ui.activities.DetailsMarvelItem
import com.example.aplicacionmovil.ui.adapters.WaifuAdapter
import com.example.aplicacionmovil.ui.utilities.Metodos
import com.flores.aplicacionmoviles.logic.data.MarvelChars
import com.flores.aplicacionmoviles.ui.adapters.MarvelAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecondFragment : Fragment() {

    private lateinit var rvAdapter: WaifuAdapter
    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager
    private var isLoading = false
    private var limit = 10
    private var offset = 0
    private var wifuItems: MutableList<WaifuChar> = mutableListOf<WaifuChar>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(
            layoutInflater,
            container,
            false
        )
        lmanager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        gManager = GridLayoutManager(requireActivity(), 2)
        val user = arguments?.getString("user")
        rvAdapter= WaifuAdapter(user!!) { sendWaifuItem(it) }
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.layaoutSimmer.visibility=View.VISIBLE
        binding.layaoutPrincipal.visibility=View.GONE
        chargeDataRVInit(offset,limit){
            binding.layaoutSimmer.visibility=View.GONE
            binding.layaoutPrincipal.visibility=View.VISIBLE
        }

        cargarDatosSwipe()
        binding.rvMarvelChars.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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


    private  fun cargarDatosSwipe(){
        binding.layaoutSimmer.visibility=View.VISIBLE
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVInit(offset,limit){
                binding.layaoutSimmer.visibility=View.GONE
            }
            binding.rvSwipe.isRefreshing = false
        }
    }

    private fun sendWaifuItem(item: WaifuChar) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun chargeDataRVInit(offset: Int, limit: Int, callback: () -> Unit)  {
        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                wifuItems = withContext(Dispatchers.IO) {
                    return@withContext (WaifuLogic().getWaifusChar(limit))
                }
                rvAdapter.items = wifuItems
                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gManager
                }
                this@SecondFragment.offset += limit
                callback()
            }
        } else {
            Snackbar.make(
                binding.cardView,
                "No hay conexion",
                Snackbar.LENGTH_LONG
            )
                .show()
        }
    }
}