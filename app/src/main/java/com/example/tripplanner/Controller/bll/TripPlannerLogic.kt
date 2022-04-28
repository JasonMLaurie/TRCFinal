package com.example.tripplanner.Controller.bll

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.widget.Toast
import com.example.tripplanner.Controller.dal.TripPlannerOperation
import com.example.tripplanner.model.ResimEntity
import com.example.tripplanner.model.ZiyaretEntity
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.activities.MainActivity
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class TripPlannerLogic {

    companion object {

        fun yerEkle(context: Context, yerEntity: YerEntity) : Boolean{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.yerEkle(yerEntity).also {
                if(it)
                    Toast.makeText(context,"Yer başarıyla eklenmiştir", Toast.LENGTH_SHORT).show()
            }
        }

        fun yerGuncelle(context: Context, yerEntity: YerEntity) : Boolean{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.yerGuncelle(yerEntity).also {
                if(it)
                    Toast.makeText(context,"Yer başarıyla güncellenmiştir.", Toast.LENGTH_SHORT).show()
            }
        }

        fun yerGetir(context: Context, locationPair : Pair<Double, Double>) : YerEntity{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.yerGetir(locationPair)
        }

        fun gezilecekleriGetir(context: Context) : ArrayList<YerEntity> {
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.gezilecekleriGetir()
        }

        fun gezdiklerimiGetir(context: Context) : ArrayList<YerEntity> {
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.gezdiklerimiGetir()
        }

        fun ziyaretEkle(context: Context, ziyaretEntity: ZiyaretEntity) : Boolean{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.ziyaretEkle(ziyaretEntity).also {
                if(it)
                    Toast.makeText(context,"Ziyaret başarıyla eklenmiştir", Toast.LENGTH_SHORT).show()
            }
        }

        fun ziyaretleriGetir(context: Context, yerEntity: YerEntity) : ArrayList<ZiyaretEntity> {
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.ziyaretleriGetir(yerEntity)
        }

        fun fotoEkle(context: Context, resimEntity: ResimEntity) : Boolean{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.fotoEkle(resimEntity).also {
                if(it)
                    Toast.makeText(context,"Resim başarıyla eklenmiştir", Toast.LENGTH_SHORT).show()
            }
        }

        fun fotolarGetir(context: Context, yerId: Int) : ArrayList<ResimEntity> {
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.fotolarGetir(yerId)
        }

        fun tumYerleriGetir(context: Context) : ArrayList<YerEntity>{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.tumYerleriGetir()
        }

        fun tumZiyaretleriGetir(context: Context) : ArrayList<ZiyaretEntity>{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.tumZiyaretleriGetir()
        }

        fun fotoGetir(context: Context, encodedImage : String) : ResimEntity{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.fotoGetir(encodedImage)
        }

        fun fotoSil(context: Context, resimEntity: ResimEntity) : Boolean{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.fotoSil(resimEntity)
        }

        // TODO Base64 in DB or a local png and URI in DB as str.

        // May need additional libraries for pre API 8, v 2.2

        fun decodeBase64(encodedImage: String) : Bitmap{
            var base64img = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(base64img!!,0,base64img.size)
        }

        fun encodeBase64(imageUri : Uri, contentResolver: ContentResolver) : String{

            val inputStream: InputStream? = contentResolver.openInputStream(imageUri)

            inputStream?.let {
                val byteArray = it.readBytes()
                var encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                return encodedImage
            }
            return ""
        }

        fun alertBuilder(
            context: Context,
            title: String,
            message: String,
            positiveString: String,
            positiveFunc : () -> Unit,
            negativeString: String,
            negativeFunc : () -> Unit,
            ){
            val adb = AlertDialog.Builder(context)
            adb.setTitle(title)
            adb.setMessage(message)

            adb.setPositiveButton(positiveString, DialogInterface.OnClickListener { dialogInterface, i ->
                positiveFunc()
            })
            adb.setNegativeButton(negativeString, DialogInterface.OnClickListener { dialogInterface, i ->
                negativeFunc()
            })

            adb.show()
        }


        /** Get Current Date from Calender */
        fun calenderFunc() : java.util.ArrayList<Int> {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dom = calendar.get(Calendar.DAY_OF_MONTH)
            return arrayListOf(dom,month,year)
        }

    }


}