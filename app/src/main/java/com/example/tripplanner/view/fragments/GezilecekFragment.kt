package com.example.tripplanner.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentGezilecekBinding


class GezilecekFragment : Fragment() {

    private lateinit var binding : FragmentGezilecekBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentGezilecekBinding.inflate(layoutInflater, container, false)

        clickListeners()

        return binding.root
    }

    private fun clickListeners(){

    }
}