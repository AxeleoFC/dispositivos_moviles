package com.flores.aplicacionmoviles.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flores.aplicacionmoviles.R
import com.flores.aplicacionmoviles.databinding.ActivityFirstFragmentBinding

class FirstFragment : Fragment() {

    private lateinit var binding:ActivityFirstFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityFirstFragmentBinding.inflate(
            layoutInflater,
            container,
            false)

        return binding.root
    }
}