package com.example.tripplanner.Controller.bll

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.tripplanner.view.activities.MainActivity
import com.example.tripplanner.view.fragments.YerEkleFragment
import com.example.tripplanner.view.fragments.ZiyaretEkleFragment
import java.io.File
import java.io.FileNotFoundException
import java.util.ArrayList

class CamMediaAccessLogic {

    companion object{
        var galleryResultLauncher: ActivityResultLauncher<Intent>?=null
        var cameraResultLauncher:ActivityResultLauncher<Intent>?=null
        var retString=""
        lateinit var resimUri:Uri



        fun initializeGalleryResultLauncher(fragment: Fragment,yerId: Int){
            galleryResultLauncher=fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
                if (result.resultCode==AppCompatActivity.RESULT_OK){
                    try {
                        resimUri=result!!.data!!.data!!
                        retString=TripPlannerLogic.encodeBase64(resimUri,(fragment.requireActivity() as MainActivity).contentResolver)

                        if (!checkIfDuplicate(fragment,yerId)){
                            if (fragment is ZiyaretEkleFragment){
                                (fragment as ZiyaretEkleFragment).addedBase64List.add(retString)
                                (fragment as ZiyaretEkleFragment).resimBase64List.add(retString)
                                (fragment as ZiyaretEkleFragment).adapter.notifyDataSetChanged()
                            }
                            else if (fragment is YerEkleFragment){
                                (fragment as YerEkleFragment).resimListe.add(retString)
                                (fragment as YerEkleFragment).fotoAdapter.notifyDataSetChanged()
                            }
                        }else{
                            Toast.makeText(fragment.requireContext(),"Sectiginiz fotograf zaten eklenmis.",Toast.LENGTH_SHORT).show()
                        }

                    }catch (e: FileNotFoundException){
                        e.printStackTrace()
                        Toast.makeText(fragment.requireContext(),"Dosya Bulunamadi",Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        /** Checks if a duplicate of this item exists in database. Returns true if there is a duplicate.*/
        private fun checkIfDuplicate(fragment: Fragment,yerId: Int):Boolean{
            val tempResimUriList : ArrayList<String> = arrayListOf()
            TripPlannerLogic.fotolarGetir(fragment.requireContext(),yerId).forEach {
                var base64 = it.base64!!
                tempResimUriList.add(base64)
            }
            if (tempResimUriList.contains(retString)){
                return true
                }
            if (fragment is ZiyaretEkleFragment){
                if ((fragment as ZiyaretEkleFragment).addedBase64List.contains(retString)){
                    return true
                }
            }else if ((fragment is YerEkleFragment)){
                if ((fragment as YerEkleFragment).resimListe.contains(retString)){
                    return true
                }
            }
            return false
        }

        /**A function to standardize fragment access to gallery. Checks for duplicates.**/
        fun getPhotoFromGallery(fragment: Fragment,yerId:Int):Pair<Boolean,String?>{
            //initializeGalleryResultLauncher(fragment)//TODO: Check if it works when this line is here-->If not, call initializeGalleryResultLauncher First
            val intent= Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            galleryResultLauncher?.launch(intent) ?: Log.e("getPhotoFromGallery","Initialize Gallery Result Launcher First")
            if (checkIfDuplicate(fragment,yerId)){
                return Pair(false,null)
            }else{
                return Pair(true, retString)
            }
        }
        /**Inititalizes cameraResutLauncher for given fragment.**/
        fun initializeCameraResultLauncher(fragment: Fragment){
            cameraResultLauncher=fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
                if (result.resultCode==AppCompatActivity.RESULT_OK){
                    try {
                        retString=TripPlannerLogic.encodeBase64(resimUri,(fragment.requireActivity() as MainActivity).contentResolver)
                        if (fragment is ZiyaretEkleFragment){
                            (fragment as ZiyaretEkleFragment).addedBase64List.add(retString)
                            (fragment as ZiyaretEkleFragment).resimBase64List.add(retString)
                            (fragment as ZiyaretEkleFragment).adapter.notifyDataSetChanged()
                        }
                        else if (fragment is YerEkleFragment){
                            (fragment as YerEkleFragment).resimListe.add(retString)
                            //(fragment as YerEkleFragment).fotoAdapter.notifyDataSetChanged()
                            (fragment as YerEkleFragment).setAdapters()
                        }
                    }catch (e: FileNotFoundException){
                        e.printStackTrace()
                        Toast.makeText(fragment.requireContext(), "Dosya bulunamadı.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        /**Creates a temporary file for camera snapshots.**/
        private fun createTempFile(fragment: Fragment): File {
            val dir=(fragment.requireActivity() as MainActivity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile("camPic",".jpg", dir).apply {
                resimUri= FileProvider.getUriForFile(fragment.requireContext(),(fragment.requireActivity() as MainActivity).packageName, this)
            }
        }

        /**A function to standardize fragment access to camera. **/
        fun getPhotoFromCamera(fragment: Fragment):String{
            val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            createTempFile(fragment)

            intent.putExtra(MediaStore.EXTRA_OUTPUT, resimUri)
            cameraResultLauncher?.launch(intent)// ?: Log.e("getPhotoFromGallery","Initialize Gallery Result Launcher First")
            return retString

        }

    }
}