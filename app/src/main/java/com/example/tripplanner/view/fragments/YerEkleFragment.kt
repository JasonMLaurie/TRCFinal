package com.example.tripplanner.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.databinding.FragmentYerEkleBinding
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.activities.MapsActivity
import com.example.tripplanner.view.adapters.foto.FotoAdapter

/** Gezilecek Yer Ekleme Fragment*/
class YerEkleFragment : Fragment() {

    private lateinit var binding: FragmentYerEkleBinding
    private var resimListe: ArrayList<Uri> = arrayListOf()
    private lateinit var locationIntent:Pair<Double,Double>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentYerEkleBinding.inflate(inflater, container, false)

        createTempList()
        setAdapters()


        var yer= YerEntity(0.0,0.0)


        binding.btnYerKaydet.setOnClickListener {
            yer=YerEntity(locationIntent.first,locationIntent.second)
            yer.yerAdi=binding.eTvYerAdi.text.toString()
            yer.kisaTanim=binding.eTvYerKisaTanim.text.toString()
            yer.kisaAciklama=binding.eTvYerKisaAciklama.text.toString()

            //todo öncelik ve fotoğraf bilgileri atamaı yapılacak

            TripPlannerLogic.yerEkle(requireContext(),yer)

            requireActivity().onBackPressed()
        }


        //TODO tabb layout düzenlenecek
        (activity as MainActivity).binding.tabLayout.isVisible=false
        (activity as MainActivity).binding.fabYerEkle.isVisible=false


        val resLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                locationIntent=Pair(it.data?.getDoubleExtra("Latitude",0.5)!!,it.data?.getDoubleExtra("Longitude",0.5)!!)
                //Toast.makeText(requireContext(),"lat: ${locationIntent.first} lon: ${locationIntent.second}",Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnKonumEkle.setOnClickListener{
            val intent= Intent(requireActivity(),MapsActivity::class.java)
            intent.putExtra("mode",false)
            resLauncher.launch(intent)

        }
        return binding.root
    }


    fun setAdapters() {

        val rvAdapter = FotoAdapter(requireContext(), resimListe, ::photoCardClickEvent)
        binding.rvYerEkle.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvYerEkle.adapter = rvAdapter

        // TODO Fill spinner with colors

    }

    fun photoCardClickEvent(){}

    /** Test Case */
    fun createTempList() {

        var i = 1
        while (i <= 3) {
            val uri: Uri =
                Uri.parse("android.resource://" + requireActivity().packageName + "/drawable/tempimage1")
            resimListe.add(uri)
            i++
        }

    }

    fun tempFuncZiyaretEkle() {

        /** Tüm yerleri getir, yer ve ziyaret ekle test case*/
/*
        TripPlannerLogic.yerEkle(requireContext(), YerEntity().apply {
            yerAdi = "Yer1"
            kisaTanim = "Tanim1"
            kisaAciklama = "Aciklama1"
            oncelik = "Oncelik1"
            ziyaretEdildi = false
        })

        val yerEntity = TripPlannerLogic.tumYerleriGetir(requireContext())[0]

        Log.e("Logcat",yerEntity.oncelik!!)

        TripPlannerLogic.ziyaretEkle(requireContext(), GezdiklerimEntity().apply {
            tarih = "11/11/11"
            aciklama = "Aciklama1"
            yerId = yerEntity.id
        })
*/

        /** Ziyaretleri getir test case */
/*        val yerEntity = TripPlannerLogic.tumYerleriGetir(requireContext())[0]

        val ziyaretList: ArrayList<GezdiklerimEntity> =
            TripPlannerLogic.ziyaretleriGetir(requireContext(), yerEntity)

        ziyaretList.forEach {
            Log.e("Logcat", it.aciklama!!)
        }*/

        /** Gezdiklerimi getir test case*/
/*
        val gezdiklerimList: ArrayList<YerEntity> =
            TripPlannerLogic.gezdiklerimiGetir(requireContext())

        gezdiklerimList.forEach {
            Log.e("Logcat", it.oncelik!!)
        }
*/

    }

//    override fun onResume() {
//        super.onResume()
//        (activity as MainActivity).binding.tabLayout.isVisible=false
//        (activity as MainActivity).binding.fabYerEkle.isVisible=false
//    }



}