package com.example.tripplanner.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tripplanner.controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentGezilecekBinding
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.adapters.yer.YerAdapter


class GezilecekFragment : Fragment() {

    lateinit var binding:FragmentGezilecekBinding
    lateinit var yerlerListe:ArrayList<YerEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding= FragmentGezilecekBinding.inflate(inflater)

       // binding.toolbar.setTitle("Gezilecekler")
        (activity as MainActivity).binding.tvToolText.setText(R.string.gezilecekler_bar_text)
        (activity as MainActivity).binding.btnBack.isVisible=false



        getTumGezilecekYerler()
        initRcv()

        return binding.root
    }

    private fun initRcv(){
        val lm = LinearLayoutManager(requireContext())
        lm.orientation= LinearLayoutManager.VERTICAL
        binding.rvGezilecekYerler.layoutManager=lm
        binding.rvGezilecekYerler.adapter=YerAdapter(requireContext(),yerlerListe,::itemClick)
    }

    fun getTumGezilecekYerler(){
        yerlerListe= TripPlannerLogic.gezilecekleriGetir(requireContext())
    }

    fun itemClick(position:Int){
        val gezilecek2DetayNavDir = GezilecekFragmentDirections.actionGezilecekFragmentToDetayFragment(yerlerListe[position])
        findNavController().navigate(gezilecek2DetayNavDir)
    }

}