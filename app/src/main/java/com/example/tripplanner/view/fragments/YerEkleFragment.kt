package com.example.tripplanner.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tripplanner.Controller.bll.CamMediaAccessLogic
import com.example.tripplanner.Controller.bll.PermissionLogic
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentYerEkleBinding
import com.example.tripplanner.model.Oncelik
import com.example.tripplanner.model.Oncelikler
import com.example.tripplanner.model.ResimEntity
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.activities.MapsActivity
import com.example.tripplanner.view.adapters.SpinnerAdapter
import com.example.tripplanner.view.adapters.foto.FotoAdapter
import java.io.File
import java.io.FileNotFoundException

/** Gezilecek Yer Ekleme Fragment*/
class YerEkleFragment : PermissionHandlingFragment() {




    private lateinit var binding: FragmentYerEkleBinding

    /** Variables */
    var resimListe: ArrayList<String> = arrayListOf("")
    lateinit var fotoAdapter: FotoAdapter
    private lateinit var locationIntent:Pair<Double,Double>
    var yer= YerEntity(0.0,0.0)
    var secilenOncelik=Oncelik(0,"")
    private lateinit var resimUri : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CamMediaAccessLogic.initializeCameraResultLauncher(this)
        CamMediaAccessLogic.initializeGalleryResultLauncher(this,-1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentYerEkleBinding.inflate(inflater, container, false)

        resimListe = arrayListOf("")
        (activity as MainActivity).binding.tvToolText.setText(R.string.yer_ekle_bar_text)
        (activity as MainActivity).binding.btnBack.isVisible=true


        setAdapters()
        clickListeners()
        setupSpinner()

        return binding.root
    }

    /** FotoAdapter set */
    fun setAdapters() {

        resimUriListCheck()

        fotoAdapter = FotoAdapter(requireContext(), resimListe, ::photoCardClickEvent, ::silmeClickEvent)
        binding.rvYerEkle.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvYerEkle.adapter = fotoAdapter

    }

    /** RecyclerView Click Events */
    // Photo addition Button Click Event
    fun photoCardClickEvent(){
        PermissionLogic.mediaPermissionControl(this,requireContext())
    }

    // Deletion Button Click Event
    fun silmeClickEvent(encodedImage : String){
            if(!resimListe.isNullOrEmpty()){
                if(resimListe.contains(encodedImage)){
                    resimListe.remove(encodedImage).apply {
                        Toast.makeText(requireContext(), "Fotoğraf silindi.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(),"Fotoğrafı silerken bir hatayla karşılaşıldı. Lütfen daha sonra tekrar deneyin"
                        , Toast.LENGTH_SHORT).show()
                }
            }
            if(resimListe.isEmpty()){ resimListe.add("") }
            setAdapters()
    }


    /** Spinner Creation Function */
    fun setupSpinner(){
        val adapter =SpinnerAdapter(requireContext(),Oncelikler.list!!)
        binding.apply {
            spinner.adapter=adapter
            spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    secilenOncelik = Oncelikler.list!!.get(position)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    /** Click Listener Functions */
    private fun clickListeners(){

        binding.btnYerKaydet.setOnClickListener {
            if(this::locationIntent.isInitialized){
                yerEkle()
                TripPlannerLogic.yerEkle(requireContext(),yer)
                fotoEkle()
            }
            else {
                Toast.makeText(requireContext(), "Konum girilmesi zorunludur.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnKonumEkle.setOnClickListener{
            val intent= Intent(requireActivity(),MapsActivity::class.java)
            intent.putExtra("mode",false)
            resLauncher.launch(intent)
        }
    }

    /** Open Gallery Func */
    override fun grantedFunc() {
        TripPlannerLogic.alertBuilder(requireContext(),"Medya Seçimi",
            "Lütfen fotoğrafı ekleme yöntemini seçiniz : ", "Kamera" ,
            ::openCamera, "Galeri" , ::openGallery)
    }

    /** Open Gallery Func*/
    private fun openGallery(){
        CamMediaAccessLogic.getPhotoFromGallery(this,-1)
        resimUriListCheck()
    }

    /** Open Camera Func */
    private fun openCamera() {

        CamMediaAccessLogic.getPhotoFromCamera(this)
        resimUriListCheck()
    }

    /** Temp File to hold the Photo*/
    fun createTempFile(): File {
        val dir = (activity as MainActivity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("camPic", ".jpg", dir).apply {
            resimUri = FileProvider.getUriForFile(requireContext(),(activity as MainActivity).packageName,this)
        }
    }



    /** Yer Ekleme function*/
    private fun yerEkle(){

        yer=YerEntity(locationIntent.first,locationIntent.second)
        yer.yerAdi=binding.eTvYerAdi.text.toString()
        yer.kisaTanim=binding.eTvYerKisaTanim.text.toString()
        yer.kisaAciklama=binding.eTvYerKisaAciklama.text.toString()
        yer.oncelik=secilenOncelik.oncelikDurumu

        requireActivity().onBackPressed()
    }

    private fun fotoEkle(){
        val dom = TripPlannerLogic.calenderFunc()[0]
        val month = TripPlannerLogic.calenderFunc()[1]
        val year = TripPlannerLogic.calenderFunc()[2]

        val tempYerEntity = TripPlannerLogic.yerGetir(requireContext(), Pair(locationIntent.first, locationIntent.second))
        resimListe.forEach {
            val resimObject = ResimEntity().apply {
                tarih = "$dom.${month+1}.$year"
                base64 = it
                yerId = tempYerEntity.id
            }
            resimUriListCheck()
            TripPlannerLogic.fotoEkle(requireContext(), resimObject)
        }

    }


    /** A function used for empty list situation. It is used to still show the add button */
    private fun resimUriListCheck(){
        if (resimListe.contains("") && resimListe.size>1){
            resimListe.remove("")
        }
    }


    override fun onResume() {
        (activity as MainActivity).binding.tabLayout.isVisible=false
        (activity as MainActivity).binding.fabYerEkle.isVisible=false
        super.onResume()
    }


    /** Result Launchers */

    // Map Selection Result
    val resLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            locationIntent=Pair(it.data?.getDoubleExtra("Latitude",0.5)!!,it.data?.getDoubleExtra("Longitude",0.5)!!)
            //Toast.makeText(requireContext(),"lat: ${locationIntent.first} lon: ${locationIntent.second}",Toast.LENGTH_SHORT).show()
        }
    }

    // Gallery Selected PhotoResult
    val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                try {
                    val imageUri: Uri = result.data!!.data!!
                    /** Base64 Usage */
                    val encodedImage = TripPlannerLogic.encodeBase64(imageUri, (activity as MainActivity).contentResolver)

                    /////////////////

                    if(!resimListe.contains(encodedImage)){
                        resimListe.add(encodedImage)
                        if (resimListe.size == 2) {
                            // TODO a more suitable solution for empty Uri list.
                            if(resimListe[0] == ""){
                                resimListe.removeAt(0)
                            }
                        }
                    }else{
                        Toast.makeText(requireContext(),"Seçilen fotoğraf zaten görüntülerde bulunuyor.", Toast.LENGTH_SHORT).show()
                    }
                    (fotoAdapter).notifyDataSetChanged()
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

                    if(!resimListe.contains(encodedImage)){
                        resimListe.add(encodedImage)
                        if (resimListe.size == 2) {
                            // TODO a more suitable solution for empty Uri list.
                            if(resimListe[0] == ""){
                                resimListe.removeAt(0)
                            }
                        }
                    }else{
                        Toast.makeText(requireContext(),"Seçilen fotoğraf zaten görüntülerde bulunuyor.", Toast.LENGTH_SHORT).show()
                    }
                    (fotoAdapter).notifyDataSetChanged()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Dosya bulunamadı.", Toast.LENGTH_LONG).show()
                }

            }
        }



}