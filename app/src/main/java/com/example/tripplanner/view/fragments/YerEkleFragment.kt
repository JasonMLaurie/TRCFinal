package com.example.tripplanner.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.databinding.FragmentYerEkleBinding
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.activities.MainActivity

/** Gezilecek Yer Ekleme Fragment*/
class YerEkleFragment : Fragment() {

    lateinit var binding: FragmentYerEkleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentYerEkleBinding.inflate(inflater)

        var yer=YerEntity()



        binding.btnYerKaydet.setOnClickListener {
            yer.yerAdi=binding.eTvYerAdi.text.toString()
            yer.kisaTanim=binding.eTvYerKisaTanim.text.toString()
            yer.kisaAciklama=binding.eTvYerKisaAciklama.text.toString()

            TripPlannerLogic.ekleGezilecekYer(requireContext(),yer)

            (activity as MainActivity).fragmentDegistir(GezilecekFragment())
        }

        (activity as MainActivity).binding.tabLayout.isVisible=false
        (activity as MainActivity).binding.fabYerEkle.isVisible=false

        return binding.root

    }






}