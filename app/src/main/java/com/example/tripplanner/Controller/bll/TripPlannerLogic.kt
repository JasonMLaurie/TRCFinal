package com.example.tripplanner.Controller.bll

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.tripplanner.Controller.dal.GezdiklerimOperation
import com.example.tripplanner.Controller.dal.GezilecekOperation
import com.example.tripplanner.model.GezdiklerimEntity
import com.example.tripplanner.model.YerEntity
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

class TripPlannerLogic {

    companion object {

        fun persistDate(date: Date?): Long? {
            return if (date != null) {
                date.getTime()
                //dsasadadsadasdsadsads
                //dasdasdsd
            } else null
        }

        // TODO Base64 in DB or a local png and URI in DB as str.

        // May need additional libraries for pre API 8, v 2.2
        fun decodeBase64(context: Context, base64ImageData : ByteArray?){
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

            var encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        }

        fun getGezilecekYerler(context: Context):ArrayList<YerEntity>{
            return GezilecekOperation(context).getTumGezilecekYer()
        }

        fun getGezdiklerimYerler(context: Context):ArrayList<GezdiklerimEntity>{
            return GezdiklerimOperation(context).getGezilecekYer()

        }

        fun ekleGezilecekYer(context: Context, item:YerEntity){
            val goA=GezilecekOperation(context)
            goA.yerEkle(item)
        }

    }

}