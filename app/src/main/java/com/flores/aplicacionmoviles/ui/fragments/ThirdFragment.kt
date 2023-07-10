package com.flores.aplicacionmoviles.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flores.aplicacionmoviles.R
import com.flores.aplicacionmoviles.databinding.ThirdFragmentBinding

class ThirdFragment : Fragment() {

    private lateinit var binding: ThirdFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ThirdFragmentBinding.inflate(
            layoutInflater,
            container,
            false)

        return binding.root
    }
}