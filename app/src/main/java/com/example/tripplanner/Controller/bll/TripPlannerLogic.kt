package com.example.tripplanner.Controller.bll

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64.encodeToString
import android.widget.Toast
import com.example.tripplanner.Controller.dal.TripPlannerOperation
import com.example.tripplanner.model.ResimEntity
import com.example.tripplanner.model.ZiyaretEntity
import com.example.tripplanner.model.YerEntity
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.lang.Exception

class TripPlannerLogic {

    companion object {

        fun yerEkle(context: Context, yerEntity: YerEntity) : Boolean{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.yerEkle(yerEntity).also {
                if(it)
                    Toast.makeText(context,"Yer başarıyla eklenmiştir", Toast.LENGTH_SHORT).show()
            }
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

        fun fotoGetir(context: Context, yerId: Int) : ArrayList<ResimEntity> {
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.fotoGetir(yerId)
        }

        fun tumYerleriGetir(context: Context) : ArrayList<YerEntity>{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.tumYerleriGetir()
        }

        fun tumZiyaretleriGetir(context: Context) : ArrayList<ZiyaretEntity>{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.tumZiyaretleriGetir()
        }



        // To Be Used Later.
/*        fun persistDate(date: Date?): Long? {
            return if (date != null) {
                date.getTime()
            } else null
        }*/

        // TODO Base64 in DB or a local png and URI in DB as str.

        // May need additional libraries for pre API 8, v 2.2
        fun decodeBase64(context:Context, base64ImageData : ByteArray?){
            var fos : FileOutputStream? = null;
            try {
                if (base64ImageData != null) {
                    fos = context.openFileOutput("imageName.png", Context.MODE_PRIVATE);
                    val decodedString : ByteArray = android.util.Base64.decode(base64ImageData, android.util.Base64.DEFAULT);
                    fos.write(decodedString);
                    fos.flush();
                    fos.close();
                }

            } catch (e : Exception) {

            } finally {
                if (fos != null) {
                    fos = null;
                }
            }
        }

        fun encodeBase64(imagePath : String){

            val bm : Bitmap = BitmapFactory.decodeFile("/path/to/image.jpg");
            var baos = ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
            var byteArrayImage : ByteArray = baos.toByteArray();

            var encodedImage = encodeToString(byteArrayImage, android.util.Base64.DEFAULT);

        }
    }


}