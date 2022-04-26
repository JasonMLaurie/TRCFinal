package com.example.tripplanner.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentDetayBinding
import com.example.tripplanner.model.Oncelik
import com.example.tripplanner.model.Oncelikler
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.model.ZiyaretEntity
import com.example.tripplanner.view.activities.MapsActivity
import com.example.tripplanner.view.adapters.foto.FotoAdapter
import com.example.tripplanner.view.adapters.ziyaret.ZiyaretAdapter

/** Gezilecek Yer Detay Fragment */
class DetayFragment : Fragment() {


    private lateinit var binding : FragmentDetayBinding
    private lateinit var yerObject : YerEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetayBinding.inflate(inflater, container, false)

//        tempSol()
        setInitialViews()
        clickListeners()

        return binding.root
    }


    /** Fill views with default values */
    @SuppressLint("SetTextI18n")
    fun setInitialViews(){
        val bundle : DetayFragmentArgs by navArgs()
        yerObject = bundle.yerObject

        binding.apply {
            tvYerKisaTanimBilgi.text = yerObject.kisaTanim
            tvKisaAciklamaBilgisi.text = yerObject.kisaAciklama
            // TODO Oncelik için kontrol
            if (yerObject.oncelik.equals("oncelik1"))//yeşil
            { ivOncelikD.setImageResource(R.drawable.oncelik1_sekil) }
            else if (yerObject.oncelik.equals("oncelik2"))//mavi
            { ivOncelikD.setImageResource(R.drawable.oncelik2_sekil) }
            else if (yerObject.oncelik.equals("oncelik3"))//gri
            { ivOncelikD.setImageResource(R.drawable.oncelik3_sekil)}
        }


        val ziyaretList = TripPlannerLogic.ziyaretleriGetir(yerEntity = yerObject, context = requireContext())
        if(!ziyaretList.isNullOrEmpty()){
            val adapter = ZiyaretAdapter(requireContext(),ziyaretList)
            binding.rvZiyaretGecmisi.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            binding.rvZiyaretGecmisi.adapter = adapter
        }

        Log.e("Logcat", TripPlannerLogic.tumZiyaretleriGetir(requireContext()).size.toString())
        Log.e("Logcat",TripPlannerLogic.tumZiyaretleriGetir(requireContext()).toString())
    }

    fun tempSol(){
        yerObject = YerEntity(0.0,0.0).apply{
            kisaAciklama = "Aciklama"
            kisaTanim = "Tanim"
            yerAdi = "Yeradi"
            oncelik = "Oncelik1"
            ziyaretEdildi = 0
        }
        TripPlannerLogic.yerEkle(requireContext(),yerObject)
        yerObject = TripPlannerLogic.tumYerleriGetir(requireContext())[0]

    }

    /** Click Listeners*/
    fun clickListeners(){

        binding.btnKonumGoster.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("Latitude",yerObject.latitude)
            intent.putExtra("Longitude",yerObject.longitude)
            intent.putExtra("mode",true)
            startActivity(intent)
        }

        binding.btnZiyaretEkle.setOnClickListener {
            val detay2ZiyaretEkleNavDir = DetayFragmentDirections.actionDetayFragmentToZiyaretEkleFragment(yerObject.id)
            Navigation.findNavController(it).navigate(detay2ZiyaretEkleNavDir)
        }

    }

    /** Refreshing views in case of any visit being added */
    override fun onResume() {
        super.onResume()
        setInitialViews()
    }

    /*
    val foodList2DetailNavDir = FoodListFragmentDirections.foodList2Detail(foodObject)
    Navigation.findNavController(it).navigate(foodList2DetailNavDir)
            */


}