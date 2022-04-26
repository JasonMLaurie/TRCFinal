package com.example.tripplanner.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentGezdiklerimBinding
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.adapters.yer.YerAdapter
import java.text.FieldPosition

/** Gezdiklerim Liste Fragment (bknz. GezilecekFragment) */
class GezdiklerimFragment : Fragment() {
    lateinit var binding: FragmentGezdiklerimBinding
    lateinit var gezdiklerimListe: ArrayList<YerEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentGezdiklerimBinding.inflate(inflater)

        getGezdiklerim()
        initRcv()

        return binding.root
    }

    private fun getGezdiklerim() {
        gezdiklerimListe= arrayListOf()
        gezdiklerimListe.mockData()
    }

    private fun initRcv(){
        val lm=LinearLayoutManager(requireContext())
        lm.orientation=LinearLayoutManager.VERTICAL
        binding.frgGezdiklerimRv.layoutManager=lm
        binding.frgGezdiklerimRv.adapter=YerAdapter(requireContext(),gezdiklerimListe,::itemClick)
        binding.frgGezdiklerimRv.apply{
            val lm=LinearLayoutManager(requireContext())
            lm.orientation=LinearLayoutManager.VERTICAL
            layoutManager=lm
        }

    }

    private fun itemClick(position: Int) {
        Toast.makeText(requireContext(),"Tiklandi",Toast.LENGTH_SHORT).show()
        var fragment=DetayFragment()
        val gezdiklerim2DetayNavDir= GezdiklerimFragmentDirections.actionGezdiklerimFragmentToDetayFragment(gezdiklerimListe[position])

        //Navigation.findNavController(requireActivity().findViewById(R.id.fragmentContainerView)).navigate(gezdiklerim2DetayNavDir)
        itemView.findNavController().navigate(gezdiklerim2DetayNavDir)
    }

    private fun ArrayList<YerEntity>.mockData(){
        var tempEntity=YerEntity(40.0,40.0)
        tempEntity.kisaAciklama="temptemptemp"
        tempEntity.kisaTanim="temp"
        tempEntity.yerAdi="TEMP"
        tempEntity.ziyaretEdildi=1
        this.add(tempEntity)
        this.add(tempEntity)
        this.add(tempEntity)
        this.add(tempEntity)
        this.add(tempEntity)
    }

}