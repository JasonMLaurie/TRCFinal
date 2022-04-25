package com.example.tripplanner.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentGezdiklerimBinding
import com.example.tripplanner.model.GezdiklerimEntity
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.adapters.yer.YerAdapter

/** Gezdiklerim Liste Fragment (bknz. GezilecekFragment) */
class GezdiklerimFragment : Fragment() {

    lateinit var binding: FragmentGezdiklerimBinding
    lateinit var yerlerListe:ArrayList<GezdiklerimEntity>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentGezdiklerimBinding.inflate(inflater)

        getTumGezdiklerim()

        return binding.root
    }

    fun getTumGezdiklerim(){
        /*yerlerListe= TripPlannerLogic.getGezdiklerimYerler(requireContext())
        val lm = LinearLayoutManager(requireContext())
        lm.orientation= LinearLayoutManager.VERTICAL
        binding.rvGezdiklerim.layoutManager=lm
        binding.rvGezdiklerim.adapter= YerAdapter(requireContext(),yerlerListe,::itemClick)*/
    }

    fun itemClick(position:Int){
        /*
        yer.id=yerlerListe.get(position).yerId
        Toast.makeText(requireContext(),// "tıklandı", Toast.LENGTH_SHORT).show()
        //findNavController().navigate(R.id.action_gezilecekFragment2_to_detayFragment)*/
    }
}