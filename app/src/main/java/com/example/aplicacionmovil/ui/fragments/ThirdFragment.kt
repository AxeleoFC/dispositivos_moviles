package com.example.aplicacionmovil.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacionmovil.R
import com.example.aplicacionmovil.databinding.FragmentThirdBinding
import com.example.aplicacionmovil.logic.data.WaifuChar
import com.example.aplicacionmovil.logic.waifuLogic.WaifuLogic
import com.example.aplicacionmovil.ui.activities.DetailsMarvelItem
import com.example.aplicacionmovil.ui.adapters.WaifuAdapter
import com.example.aplicacionmovil.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding
    private lateinit var rvAdapter: WaifuAdapter
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager
    private var fireBase = Firebase.firestore
    private var wifuItems: MutableList<WaifuChar> = mutableListOf<WaifuChar>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentThirdBinding.inflate(
            layoutInflater,
            container,
            false)
        gManager = GridLayoutManager(requireContext(), 2)

        return binding.root
    }
    override fun onStart() {
        super.onStart()
        val user = arguments?.getString("user")
        if (user != null) {
            binding.layaoutSimmer.visibility = View.VISIBLE
            getData(user)
            binding.rvSwipe.setOnRefreshListener {
                binding.layaoutSimmer.visibility = View.VISIBLE
                getData(user)
            }
        }
    }

    private fun sendWaifuItem(item: WaifuChar) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    private fun getData(user:String) {
        val adapter = WaifuAdapter(""
        ) { sendWaifuItem(it) }
        val rvDatos = binding.rvMarvelChars
        rvDatos.adapter = adapter
        binding.rvMarvelChars.apply {
            this.adapter = adapter // Usar el adaptador 'adapter' aquí
            this.layoutManager = gManager
        }
        getWaifus(user) { eventos ->
            adapter.updateData(eventos)
        }
    }

    private fun getWaifus(user: String, callback: (ArrayList<WaifuChar>) -> Unit) {
        val TAG = "Waifu"
        val eventosList = ArrayList<WaifuChar>()
        fireBase.collection("waifus")
            .whereEqualTo("user_w", user)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    val name = data["names"] as String
                    eventosList.add(
                        WaifuChar(
                            (data["_id"] as? Long)?.toInt() ?: 0,
                            data["from"] as String,
                            data["images"] as String,
                            data["names"] as String, // Use the stored name
                            (data["statistics"] as? Long)?.toInt() ?: 0,
                            ""
                        )
                    )
                }
                if (eventosList.isEmpty()) {
                    showSnackbar("Aun no ingresa ningun favorito.")
                }
                callback(eventosList)
                binding.layaoutSimmer.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                binding.layaoutSimmer.visibility = View.GONE
            }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}