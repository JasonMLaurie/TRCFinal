package com.example.tripplanner.view.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentYerEkleBinding
import com.example.tripplanner.view.adapters.foto.FotoAdapter

/** Gezilecek Yer Ekleme Fragment*/
class YerEkleFragment : Fragment() {

    private lateinit var binding : FragmentYerEkleBinding
    private var resimListe : ArrayList<Uri> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentYerEkleBinding.inflate(inflater, container, false)

        createTempList()
        setAdapters()

        return binding.root
    }

    fun setAdapters(){

        val rvAdapter = FotoAdapter(requireContext(),resimListe)
        binding.rvYerEkle.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvYerEkle.adapter = rvAdapter

        // TODO Fill spinner with colors

    }

    /** Test Case */
    fun createTempList(){

        var i = 1
        while(i<=7){
            val uri : Uri = Uri.parse("android.resource://" + requireActivity().packageName + "/drawable/tempimage1");
            resimListe.add(uri)
            i++
        }

    }

}