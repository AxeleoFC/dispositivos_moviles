package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.logic.validator.jkanLogic.JikanAnimeLogic
import com.example.dispositivosmoviles.logic.validator.jkanLogic.MarvleLogic

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var  lmanager: LinearLayoutManager
    private var rvAdapter: MarvelAdapters=MarvelAdapters{sendMarvelItem(it)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)

        lmanager=LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val  list= arrayListOf<String>("A","B","c")
        val adapter=ArrayAdapter<String>(requireActivity(),R.layout.simple_spinner_layout,list)
        binding.spinner.adapter=adapter

        chargeDataCh("Spider")
        binding.rvSwipe.setOnRefreshListener {
            chargeDataCh("Spider")
            binding.rvSwipe.isRefreshing=false
        }

        //Para cargar más contenido
        binding.rvMarvelChars.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val elementos =lmanager.childCount
                val posicion=lmanager.findFirstVisibleItemPosition()
                val tamanio=lmanager.itemCount

                if(dy>0){
                    if((elementos+posicion)>=tamanio) {
                        chargeDataCh("spider")
                        lifecycleScope.launch((Dispatchers.IO)){
                            val items= JikanAnimeLogic().getAllAnimes()
                                //MarvleLogic().getMarvelChars("cap",7)
                            withContext(Dispatchers.Main){
                                rvAdapter.updateListItems(items)
                            }
                        }
                    }
                }
            }
        })
    }
    fun sendMarvelItem(item:MarvelChars){
        val i=Intent(requireActivity(),DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun corrotime(){
        lifecycleScope.launch(Dispatchers.Main){
            lifecycleScope.launch(Dispatchers.IO){
                var name="Axel"
                name= withContext(Dispatchers.IO){
                    name="Leo"
                    return@withContext name
                }
                binding.cardView.radius
            }
        }
    }

    fun chargeDataCh(search:String){
        lifecycleScope.launch(Dispatchers.IO){
            rvAdapter.items = JikanAnimeLogic().getAllAnimes()

            withContext(Dispatchers.Main){
                with(binding.rvMarvelChars){

                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }
            }
        }
    }



}