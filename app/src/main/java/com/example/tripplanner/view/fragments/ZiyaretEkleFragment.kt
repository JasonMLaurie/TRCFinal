package com.example.tripplanner.view.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.bll.PermissionLogic
import com.example.tripplanner.bll.TripPlannerLogic
import com.example.tripplanner.databinding.FragmentZiyaretEkleBinding
import com.example.tripplanner.model.ZiyaretEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.adapters.foto.FotoAdapter
import java.io.FileNotFoundException
import java.util.*


/** Ziyaret Ekleme Fragment
 */
class ZiyaretEkleFragment : Fragment() {

    private lateinit var binding : FragmentZiyaretEkleBinding
    private var resimUriList: ArrayList<Uri> = arrayListOf(Uri.EMPTY)
    lateinit var adapter: FotoAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentZiyaretEkleBinding.inflate(inflater, container, false)

//        createTempList()
        setInitialViews()
        clickListeners()

        return binding.root
    }

    /** Fill views with default values */
    @SuppressLint("SetTextI18n")
    fun setInitialViews(){
        adapter = FotoAdapter(requireContext(), resimUriList, ::photoCardClickEvent)
        binding.rvZiyaretEkle.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvZiyaretEkle.adapter = adapter

        // Set Date to current.
        val dateList = calenderFunc()
        binding.tvTarihEkle.text = "${dateList[0]}.${dateList[1] +1}.${dateList[2]}"

        // Create line limiter for explanation
        textWatchers()
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
                tarih = binding.tvTarihEkle.toString()
                aciklama = binding.etZiyaretEkleAciklama.toString()
                // yerId = intent.getInt("yerId")
                // TODO get YerId from DetailsFragment
            }
            // TODO restriction to make Ziyaret Yer field non-null-0
            TripPlannerLogic.ziyaretEkle(requireContext(),ziyaretEntity)
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
                    resimUriList.add(imageUri)
                    if (resimUriList.size == 2) {
                        // TODO a more suitable solution for empty Uri list.
                            if(resimUriList[0].equals(Uri.EMPTY)){
                                resimUriList.removeAt(0)
                            }
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
        // It asks for permission but opens gallery before it. If a photo is selected then it returns
        // to the source page where the permission pop up still up, and if you permit it, it works as
        // intended, but if you deny the permission it still adds the selected photo from gallery,
        // and this process is doable indefinitely.
        // Remove condition check to reproduce it.

        // Made mediaPermissionControl return a boolean value for a temp. (or definite) solution
        if(PermissionLogic.mediaPermissionControl((activity as MainActivity),requireContext())){
            //V1
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
}