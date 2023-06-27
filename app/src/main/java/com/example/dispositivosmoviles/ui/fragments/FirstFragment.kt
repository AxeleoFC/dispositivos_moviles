package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.list.ListItems
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.ui.activities.MainActivity
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.dispositivosmoviles.logic.validator.jkanLogic.JikanAnimeLogic
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.logic.validator.jkanLogic.MarvleLogic

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val  list= arrayListOf<String>("A","B","c")
        val adapter=ArrayAdapter<String>(requireActivity(),R.layout.simple_spinner_layout,list)
        binding.spinner.adapter=adapter

        chargeMarvelItem()
        binding.rvSwipe.setOnRefreshListener {
            chargeMarvelItem()
            binding.rvSwipe.isRefreshing=false
        }
    }
    fun sendMarvelItem(item:MarvelChars){
        val i=Intent(requireActivity(),DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun chargeMarvelItem(){

        lifecycleScope.launch(Dispatchers.IO){
            val rvAdapter = MarvelAdapters(
                MarvleLogic().getMarvelChars("Spide",10)
            ) { sendMarvelItem(it) }

            withContext(Dispatchers.Main){
                with(binding.rvMarvelChars){

                    this.adapter = rvAdapter
                    this.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                }

            }


        }

    }


}