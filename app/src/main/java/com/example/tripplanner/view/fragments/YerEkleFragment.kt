package com.example.tripplanner.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.Controller.bll.PermissionLogic
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.databinding.FragmentYerEkleBinding
import com.example.tripplanner.model.Oncelik
import com.example.tripplanner.model.Oncelikler
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.activities.MapsActivity
import com.example.tripplanner.view.adapters.SpinnerAdapter
import com.example.tripplanner.view.adapters.foto.FotoAdapter

/** Gezilecek Yer Ekleme Fragment*/
class YerEkleFragment : PermissionHandlingFragment() {




    private lateinit var binding: FragmentYerEkleBinding
    private var resimListe: ArrayList<String> = arrayListOf("")
    private lateinit var locationIntent:Pair<Double,Double>
    var yer= YerEntity(0.0,0.0)
    var secilenOncelik=Oncelik(0,"")

    override fun grantedFunc(){
        Toast.makeText(requireContext(),"toast",Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentYerEkleBinding.inflate(inflater, container, false)

        createTempList()
        setAdapters()
        setupSpinner()





        binding.btnYerKaydet.setOnClickListener {
            if(this::locationIntent.isInitialized){
                yerEkle()
                TripPlannerLogic.yerEkle(requireContext(),yer)
            }
            else {
            Toast.makeText(requireContext(), "Konum girilmesi zorunludur.", Toast.LENGTH_SHORT).show()
            }



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


    fun yerEkle(){

        yer=YerEntity(locationIntent.first,locationIntent.second)
        yer.yerAdi=binding.eTvYerAdi.text.toString()
        yer.kisaTanim=binding.eTvYerKisaTanim.text.toString()
        yer.kisaAciklama=binding.eTvYerKisaAciklama.text.toString()
        yer.oncelik=secilenOncelik.oncelikDurumu

        //todo fotoğraf bilgileri atamaı yapılacak

        requireActivity().onBackPressed()
    }
    fun setAdapters() {

        resimUriListCheck()

        val rvAdapter = FotoAdapter(requireContext(), resimListe, ::photoCardClickEvent, ::silmeClickEvent)
        binding.rvYerEkle.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvYerEkle.adapter = rvAdapter

        // TODO Fill spinner with colors

    }

    fun photoCardClickEvent(){
        PermissionLogic.mediaPermissionControl(this,requireContext())
    }


    /** Photo deletion function.*/
    fun silmeClickEvent(encodedImage : String){
/*            val tempResimObject = TripPlannerLogic.fotoGetir(requireContext(), uri.toString())
            if(tempResimObject.uri.isNullOrEmpty()){
                if(resimListe.contains(uri)){
                    resimListe.remove(uri).apply {
                        Toast.makeText(requireContext(), "Fotoğraf silindi.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(),"Fotoğrafı silerken bir hatayla karşılaşıldı. Lütfen daha sonra tekrar deneyin"
                        , Toast.LENGTH_SHORT).show()
                }
            }else{
                TripPlannerLogic.fotoSil(requireContext(), tempResimObject).apply {
                    Toast.makeText(requireContext(), "Fotoğraf silindi.", Toast.LENGTH_SHORT).show()
                }
                resimListe.remove(uri)
            }
            if(resimListe.isEmpty()){ resimListe.add(Uri.EMPTY) }
            setAdapters()*/
        }


    /** A function used for empty list situation. It is used to still show the add button */
    private fun resimUriListCheck(){
        if (resimListe.contains("") && resimListe.size>1){
            resimListe.remove("")
        }
    }

    /** Test Case */
    fun createTempList() {

        var i = 1
        while (i <= 3) {
            val uri: Uri =
                Uri.parse("android.resource://" + requireActivity().packageName + "/drawable/tempimage1")
            val encodedImage = TripPlannerLogic.encodeBase64(uri, (activity as MainActivity).contentResolver)
            resimListe.add(encodedImage)
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

    override fun onResume() {
        (activity as MainActivity).binding.tabLayout.isVisible=false
        (activity as MainActivity).binding.fabYerEkle.isVisible=false
        super.onResume()
    }


    fun setupSpinner(){
        val adapter =SpinnerAdapter(requireContext(),Oncelikler.list!!)
        binding.apply {
            spinner.adapter=adapter
            spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    //var secilenOncelik = parent!!.getItemAtPosition(position)
                    secilenOncelik = Oncelikler.list!!.get(position)
                    Toast.makeText(requireContext(),"${secilenOncelik.oncelikDurumu}",Toast.LENGTH_SHORT).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

    }




}