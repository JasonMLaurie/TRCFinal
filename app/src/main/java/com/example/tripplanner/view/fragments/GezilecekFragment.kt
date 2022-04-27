package com.example.tripplanner.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.databinding.FragmentGezilecekBinding
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.adapters.yer.YerAdapter


class GezilecekFragment : Fragment() {

    lateinit var binding:FragmentGezilecekBinding
    lateinit var yerlerListe:ArrayList<YerEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentGezilecekBinding.inflate(inflater)

        getTumGezilecekYerler()

        return binding.root
    }

    fun getTumGezilecekYerler(){

        yerlerListe= TripPlannerLogic.gezilecekleriGetir(requireContext())
        val lm = LinearLayoutManager(requireContext())
        lm.orientation= LinearLayoutManager.VERTICAL
        binding.rvGezilecekYerler.layoutManager=lm
        binding.rvGezilecekYerler.adapter=YerAdapter(requireContext(),yerlerListe,::itemClick)
    }

    fun itemClick(position:Int,itemView: View){
        Toast.makeText(requireContext(),yerlerListe.get(position).yerAdi + " tıklandı", Toast.LENGTH_SHORT).show()
//        findNavController().navigate(R.id.action_gezilecekFragment2_to_detayFragment)
//        (activity as MainActivity).fragmentDegistir(DetayFragment())

        val gezilecek2DetayNavDir = GezilecekFragmentDirections.actionGezilecekFragment2ToDetayFragment(yerlerListe[position])
        (activity as MainActivity).fragmentDegistir(gezilecek2DetayNavDir)
    }

}