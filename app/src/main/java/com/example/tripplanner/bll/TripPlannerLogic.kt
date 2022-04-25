package com.example.tripplanner.bll

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64.encodeToString
import android.widget.Toast
import com.example.tripplanner.dal.TripPlannerOperation
import com.example.tripplanner.model.GezdiklerimEntity
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.view.fragments.YerEkleFragment
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

        fun ziyaretEkle(context: Context, gezdiklerimEntity: GezdiklerimEntity) : Boolean{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.ziyaretEkle(gezdiklerimEntity).also {
                if(it)
                    Toast.makeText(context,"Ziyaret başarıyla eklenmiştir", Toast.LENGTH_SHORT).show()
            }
        }

        fun tumYerleriGetir(context: Context) : ArrayList<YerEntity>{
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.tumYerleriGetir()
        }

        fun ziyaretleriGetir(context: Context, yerEntity: YerEntity) : ArrayList<GezdiklerimEntity> {
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.ziyaretleriGetir(yerEntity)
        }

        fun gezdiklerimiGetir(context: Context) : ArrayList<YerEntity> {
            val tripPlannerOperation = TripPlannerOperation(context)
            return tripPlannerOperation.gezdiklerimiGetir()
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