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
import java.io.File
import java.io.FileNotFoundException
import java.util.ArrayList

class CamMediaAccessLogic {

    companion object{
        var galleryResultLauncher: ActivityResultLauncher<Intent>?=null
        var cameraResultLauncher:ActivityResultLauncher<Intent>?=null
        var retString=""
        lateinit var resimUri:Uri

        private fun initializeGalleryResultLauncher(fragment: Fragment){
            galleryResultLauncher=fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
                if (result.resultCode==AppCompatActivity.RESULT_OK){
                    try {
                        val imageUri: Uri =result!!.data!!.data!!

                        retString=TripPlannerLogic.encodeBase64(imageUri,(fragment.requireActivity() as MainActivity).contentResolver)


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
            return false
        }

        /**A function to standardize fragment access to gallery. Checks for duplicates.**/
        fun getPhotoFromGallery(fragment: Fragment,yerId:Int):Pair<Boolean,String?>{
            initializeGalleryResultLauncher(fragment)//TODO: Check if it works when this line is here-->If not, call initializeGalleryResultLauncher First
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
        private fun initializeCameraResultLauncher(fragment: Fragment){
            cameraResultLauncher=fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
                if (result.resultCode==AppCompatActivity.RESULT_OK){
                    try {
                        retString=TripPlannerLogic.encodeBase64(resimUri,(fragment.requireActivity() as MainActivity).contentResolver)

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
        fun getPhotoFromCamera(fragment: Fragment):String?{
            val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            createTempFile(fragment)

            initializeCameraResultLauncher(fragment)//TODO: Check if it works when this line is here-->If not, call initializeCameraResultLauncher First

            intent.putExtra(MediaStore.EXTRA_OUTPUT, resimUri)
            cameraResultLauncher?.launch(intent) ?: Log.e("getPhotoFromGallery","Initialize Gallery Result Launcher First")
            return retString
        }

    }
}