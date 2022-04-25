package com.example.tripplanner.view.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.databinding.FragmentYerEkleBinding
import com.example.tripplanner.view.adapters.foto.FotoAdapter

/** Gezilecek Yer Ekleme Fragment*/
class YerEkleFragment : Fragment() {

    private lateinit var binding: FragmentYerEkleBinding
    private var resimListe: ArrayList<Uri> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentYerEkleBinding.inflate(inflater, container, false)

        createTempList()
        setAdapters()

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

}