package com.example.tripplanner.view.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.Controller.bll.PermissionLogic
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.databinding.FragmentZiyaretEkleBinding
import com.example.tripplanner.model.ResimEntity
import com.example.tripplanner.model.ZiyaretEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.activities.PermissionActivity
import com.example.tripplanner.view.adapters.foto.FotoAdapter
import java.io.FileNotFoundException
import java.util.*


/** Ziyaret Ekleme Fragment
 */
class ZiyaretEkleFragment : Fragment() {


    private lateinit var binding : FragmentZiyaretEkleBinding
    private lateinit var adapter: FotoAdapter

    /** Variables */
    private var resimUriList: ArrayList<Uri> = arrayListOf(Uri.EMPTY)
    private var addedUriList : ArrayList<Uri> = arrayListOf()
    private var gelenYerId : Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentZiyaretEkleBinding.inflate(inflater, container, false)

        addedUriList = arrayListOf()


        val bundle : ZiyaretEkleFragmentArgs by navArgs()
        gelenYerId = bundle.yerId

//        createTempList()
        setInitialViews()
        clickListeners()

        return binding.root
    }

    /** Fill views with default values */
    @SuppressLint("SetTextI18n")
    fun setInitialViews(){

        fotolarıAl()
        resimUriListCheck()

        adapter = FotoAdapter(requireContext(), resimUriList, ::photoCardClickEvent, ::resimSilClick)
        binding.rvZiyaretEkle.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvZiyaretEkle.adapter = adapter

        // Set Date to current.
        val dateList = calenderFunc()
        binding.tvTarihEkle.text = "${dateList[0]}.${dateList[1] +1}.${dateList[2]}"

        // Create line limiter for explanation
        textWatchers()
    }

    /** Getting all photos of current YerEntity.*/
    private fun fotolarıAl(){
        val resimList = TripPlannerLogic.fotolarGetir(requireContext() ,gelenYerId)

        resimList.forEach {
            Log.e("LogcatResim",it.uri.toString())
            var uri = Uri.parse(it.uri)
            if(!resimUriList.contains(uri)){
                resimUriList.add(uri)
            }
        }

    }

    /** A function used for empty list situation. It is used to still show the add button */
    private fun resimUriListCheck(){
        if (resimUriList.contains(Uri.EMPTY) && resimUriList.size>1){
            resimUriList.remove(Uri.EMPTY)
        }
    }

    /** Photo deletion function.*/
    private fun resimSilClick(uri : Uri){
        val tempResimObject = TripPlannerLogic.fotoGetir(requireContext(), uri.toString())
        if(tempResimObject.uri.isNullOrEmpty()){
            if(addedUriList.contains(uri)){
                resimUriList.remove(uri)
                addedUriList.remove(uri).apply {
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
            resimUriList.remove(uri)
        }
        if(resimUriList.isEmpty()){ resimUriList.add(Uri.EMPTY) }
        setInitialViews()
    }


    /** Test Case */
    fun createTempList() {

        var i = 1
        while (i <= 3) {
            val uri: Uri =
                Uri.parse("android.resource://" + requireActivity().packageName + "/drawable/tempimage1")
            resimUriList.add(uri)
            i++
        }

    }

    /** Click Event for Adapter */
    fun photoCardClickEvent(){
        if(resimUriList.size<10){
            openGallery()
        }else{
            Toast.makeText(requireContext(),"10 adetten fazla fotoğraf eklenemez", Toast.LENGTH_SHORT).show()
        }
    }

    /** Click Listeners */
    fun clickListeners(){

        binding.btnZiyaretKaydet.setOnClickListener {

            val ziyaretEntity = ZiyaretEntity().apply {
                tarih = binding.tvTarihEkle.text.toString()
                aciklama = binding.etZiyaretEkleAciklama.text.toString()
                yerId = gelenYerId
            }
            // TODO restriction to make Ziyaret Yer field non-null-0
            TripPlannerLogic.ziyaretEkle(requireContext(),ziyaretEntity)

            addedUriList.forEach {
                val resimEntity = ResimEntity().apply {
                    uri = it.toString()
                    yerId = gelenYerId
                }
                TripPlannerLogic.fotoEkle(requireContext(),resimEntity)
            }

            (activity as MainActivity).binding.tabLayout.isVisible=true
            (activity as MainActivity).binding.fabYerEkle.isVisible=true
            requireActivity().onBackPressed()
        }

        binding.clZiyaretEkleTarih.setOnClickListener {
            customDatePicker(calenderFunc())
        }

    }

    /** Text Watcher */
    fun textWatchers(){
        var lastText = binding.etZiyaretEkleAciklama.text.toString()
        // Set listener to wishDescriptionEditText in order to limit line number
        binding.etZiyaretEkleAciklama.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etZiyaretEkleAciklama.getLineCount() > 3) {
                    binding.etZiyaretEkleAciklama.setText(lastText);
                    Toast.makeText(requireContext(), "Maksimum 3 satır açıklama girilebilir", Toast.LENGTH_LONG).show();
                } else {
                    lastText = binding.etZiyaretEkleAciklama.text.toString();
                }
            }
        });
    }

    /** Result Launchers */
    // Gallery Selected PhotoResult
    val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == AppCompatActivity.RESULT_OK) {

                try {

                    val imageUri: Uri = result.data!!.data!!

                    // Get resimList from DB to check if selected photo is already exits.
                    val tempResimUriList : ArrayList<Uri> = arrayListOf()
                        TripPlannerLogic.fotolarGetir(requireContext(),gelenYerId).forEach {
                            var uri = Uri.parse(it.uri)
                            tempResimUriList.add(uri)
                    }

                    if(!tempResimUriList.contains(imageUri) && !addedUriList.contains(imageUri)){
                        resimUriList.add(imageUri)
                        addedUriList.add(imageUri)
                        if (resimUriList.size == 2) {
                            // TODO a more suitable solution for empty Uri list.
                            if(resimUriList[0].equals(Uri.EMPTY)){
                                resimUriList.removeAt(0)
                            }
                        }
                    }else{
                        Toast.makeText(requireContext(),"Seçilen fotoğraf zaten görüntülerde bulunuyor.", Toast.LENGTH_SHORT).show()
                    }
                    (adapter).notifyDataSetChanged()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Dosya bulunamadı.", Toast.LENGTH_LONG).show()
                }
            }
        }

    /** Open Gallery Func */
    fun openGallery() {

        // TODO Permission problem here. Maybe about SDK.
        /** Notes From ARAS: PermissionLogic requires an activity specifically derived from PermissionActivity. Since this is a fragment it will not work as intended.
         * For now it might be better of with manual permission requests. After the project is done, Permission Activity should be changed into an interface */
        // It asks for permission but opens gallery before it. If a photo is selected then it returns
        // to the source page where the permission pop up still up, and if you permit it, it works as
        // intended, but if you deny the permission it still adds the selected photo from gallery,
        // and this process is doable indefinitely.
        // Remove condition check to reproduce it.

        // Made mediaPermissionControl return a boolean value for a temp. (or definite) solution
        if(PermissionLogic.mediaPermissionControl((activity as PermissionActivity),requireContext())){
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            galleryResultLauncher.launch(intent)
        }else{
            Toast.makeText(requireContext(),"This app needs specified permissions", Toast.LENGTH_SHORT).show()
        }

    }

    /** Get Current Date from Calender */
    private fun calenderFunc() : ArrayList<Int> {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dom = calendar.get(Calendar.DAY_OF_MONTH)
        return arrayListOf(dom,month,year)
    }

    /** Date picker */
    @SuppressLint("SetTextI18n")
    private fun customDatePicker(dateList : ArrayList<Int>){

        val dp = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dom ->
            binding.tvTarihEkle.text = "$dom.${month+1}.$year"
        }, dateList[2], dateList[1], dateList[0])

        dp.datePicker.maxDate = System.currentTimeMillis()
        dp.setButton(DialogInterface.BUTTON_POSITIVE, "Seç", dp)
        dp.setButton(DialogInterface.BUTTON_NEGATIVE, "İptal", dp)
        dp.show()
    }

    /** Refreshing the views and hiding tablayout just in case.*/
    override fun onResume() {
        super.onResume()
        setInitialViews()
        (activity as MainActivity).binding.tabLayout.isVisible=false
        (activity as MainActivity).binding.fabYerEkle.isVisible=false
    }
}