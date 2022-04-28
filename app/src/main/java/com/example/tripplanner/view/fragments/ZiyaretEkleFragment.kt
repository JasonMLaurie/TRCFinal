package com.example.tripplanner.view.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.Controller.bll.PermissionLogic
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentZiyaretEkleBinding
import com.example.tripplanner.model.ResimEntity
import com.example.tripplanner.model.ZiyaretEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.adapters.foto.FotoAdapter
import java.io.File
import java.io.FileNotFoundException
import java.util.*


/** Ziyaret Ekleme Fragment
 */
class ZiyaretEkleFragment : PermissionHandlingFragment() {


    private lateinit var binding : FragmentZiyaretEkleBinding
    private lateinit var adapter: FotoAdapter

    /** Variables */
    private var resimBase64List: ArrayList<String> = arrayListOf("")
    private var addedBase64List : ArrayList<String> = arrayListOf()
    private var gelenYerId : Int = 0


    private lateinit var resimUri : Uri

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentZiyaretEkleBinding.inflate(inflater, container, false)

        onceOnCreate()
        setInitialViews()
        clickListeners()

        return binding.root
    }

    private fun onceOnCreate(){
        addedBase64List = arrayListOf()


        val bundle : ZiyaretEkleFragmentArgs by navArgs()
        gelenYerId = bundle.yerId


        // Set Date to current.
        val dateList = TripPlannerLogic.calenderFunc()
        binding.tvTarihEkle.text = "${dateList[0]}.${dateList[1] +1}.${dateList[2]}"


        // Create line limiter for explanation
        textWatchers()
    }

    /** Fill views with default values */
    @SuppressLint("SetTextI18n")
    private fun setInitialViews(){

        fotolarıAl()
        resimUriListCheck()

        adapter = FotoAdapter(requireContext(), resimBase64List, ::photoCardClickEvent, ::resimSilClick)
        binding.rvZiyaretEkle.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvZiyaretEkle.adapter = adapter

    }

    /** Getting all photos of current YerEntity.*/
    private fun fotolarıAl(){

        val resimList = TripPlannerLogic.fotolarGetir(requireContext() ,gelenYerId)

        resimList.forEach {
            if(!resimBase64List.contains(it.base64)){
                resimBase64List.add(it.base64!!)
            }
        }

    }

    /** A function used for empty list situation. It is used to still show the add button */
    private fun resimUriListCheck(){
        if (resimBase64List.contains("") && resimBase64List.size>1){
            resimBase64List.remove("")
        }
    }

    /** Photo deletion function.*/
    private fun resimSilClick(encodedImage : String){
        val tempResimObject = TripPlannerLogic.fotoGetir(requireContext(), encodedImage)
        if(tempResimObject.base64.isNullOrEmpty()){
            if(addedBase64List.contains(encodedImage)){
                resimBase64List.remove(encodedImage)
                addedBase64List.remove(encodedImage).apply {
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
            resimBase64List.remove(encodedImage)
        }
        if(resimBase64List.isEmpty()){ resimBase64List.add("") }
        setInitialViews()
    }

    /** Click Event for Adapter */
    fun photoCardClickEvent(){
        if(resimBase64List.size<10){
            //openGallery()
            PermissionLogic.mediaPermissionControl(this,requireContext())


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

            addedBase64List.forEach {
                val resimEntity = ResimEntity().apply {
                    base64 = it
                    yerId = gelenYerId
                    tarih=binding.tvTarihEkle.text.toString()
                }
                TripPlannerLogic.fotoEkle(requireContext(),resimEntity)
            }

            (activity as MainActivity).binding.tabLayout.isVisible=true
            (activity as MainActivity).binding.fabYerEkle.isVisible=true
            requireActivity().onBackPressed()
        }

        binding.clZiyaretEkleTarih.setOnClickListener {
            customDatePicker(TripPlannerLogic.calenderFunc())
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

    /** Open Gallery Func */
    override fun grantedFunc() {
        TripPlannerLogic.alertBuilder(requireContext(),"Medya Seçimi",
            "Lütfen fotoğrafı ekleme yöntemini seçiniz : ", "Kamera" ,
            ::openCamera, "Galeri" , ::openGallery)
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        galleryResultLauncher.launch(intent)
    }

    /** Open Camera Func */
    private fun openCamera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createTempFile()
//        val tempFile = createTempFile()
//        resimUri = FileProvider.getUriForFile(this, packageName, tempFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, resimUri)
        cameraResultLauncher.launch(intent)
    }

    /** Temp File to hold the Photo*/
    fun createTempFile(): File {
        val dir = (activity as MainActivity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("camPic", ".jpg", dir).apply {
            resimUri = FileProvider.getUriForFile(requireContext(),(activity as MainActivity).packageName,this)
        }
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

    /** Result Launchers */
    // Gallery Selected PhotoResult
    val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == AppCompatActivity.RESULT_OK) {

                try {

                    val imageUri: Uri = result.data!!.data!!

                    /** Base64 Usage */
                    val encodedImage = TripPlannerLogic.encodeBase64(imageUri, (activity as MainActivity).contentResolver)

                    /////////////////

                    // Get resimList from DB to check if selected photo is already exits.
                    val tempResimUriList : ArrayList<String> = arrayListOf()
                    TripPlannerLogic.fotolarGetir(requireContext(),gelenYerId).forEach {
                        var base64 = it.base64!!
                        tempResimUriList.add(base64)
                    }

                    if(!tempResimUriList.contains(encodedImage) && !addedBase64List.contains(encodedImage)){
                        resimBase64List.add(encodedImage)
                        addedBase64List.add(encodedImage)
                        if (resimBase64List.size == 2) {
                            // TODO a more suitable solution for empty Uri list.
                            if(resimBase64List[0].equals("")){
                                resimBase64List.removeAt(0)
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

    // Camera Shot Result
    val cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                try {
                    val encodedImage = TripPlannerLogic.encodeBase64(resimUri, (activity as MainActivity).contentResolver)

                    // Get resimList from DB to check if selected photo is already exits.
                    val tempResimUriList : ArrayList<String> = arrayListOf()
                    TripPlannerLogic.fotolarGetir(requireContext(),gelenYerId).forEach {
                        var base64 = it.base64!!
                        tempResimUriList.add(base64)
                    }

                    if(!tempResimUriList.contains(encodedImage) && !addedBase64List.contains(encodedImage)){
                        resimBase64List.add(encodedImage)
                        addedBase64List.add(encodedImage)
                        if (resimBase64List.size == 2) {
                            // TODO a more suitable solution for empty Uri list.
                            if(resimBase64List[0].equals("")){
                                resimBase64List.removeAt(0)
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


}