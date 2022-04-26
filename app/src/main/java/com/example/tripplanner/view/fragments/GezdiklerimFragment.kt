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
import com.example.tripplanner.model.ZiyaretEntity
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
       /* var temp=TripPlannerLogic.tumYerleriGetir(requireContext())[3]
        var ziyaret=ZiyaretEntity()
        ziyaret.aciklama=temp.kisaAciklama
        ziyaret.tarih="01.01.01"
        ziyaret.yerId=temp.id
        TripPlannerLogic.ziyaretEkle(requireContext(),ziyaret)*/

        gezdiklerimListe= TripPlannerLogic.gezdiklerimiGetir(requireContext())
    }

    private fun initRcv(){
        val lm=LinearLayoutManager(requireContext())
        lm.orientation=LinearLayoutManager.VERTICAL
        binding.frgGezdiklerimRv.layoutManager=lm
        binding.frgGezdiklerimRv.adapter=YerAdapter(requireContext(),gezdiklerimListe,::itemClick)
    }

    private fun itemClick(position: Int,itemView:View) {
        Toast.makeText(requireContext(),"Tiklandi",Toast.LENGTH_SHORT).show()
        var fragment=DetayFragment()

        fragment.yerObject=gezdiklerimListe[position]
        (requireActivity() as MainActivity).fragmentDegistir(fragment)
        //Navigation.findNavController(requireActivity().findViewById(R.id.fragmentContainerView)).navigate(gezdiklerim2DetayNavDir)
        //itemView.findNavController().navigate(gezdiklerim2DetayNavDir)
    }

    private fun ArrayList<YerEntity>.mockData(){
        var tempEntity=YerEntity(40.0,40.0)
        tempEntity.kisaAciklama="temptemptemp"
        tempEntity.kisaTanim="temp"
        tempEntity.yerAdi="TEMP"
        tempEntity.ziyaretEdildi=1
        tempEntity.id=1
        this.add(tempEntity)
        this.add(tempEntity)
        this.add(tempEntity)
        this.add(tempEntity)
        this.add(tempEntity)
    }

}