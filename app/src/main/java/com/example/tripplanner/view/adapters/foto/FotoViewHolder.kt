package com.example.tripplanner.view.adapters.foto

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.controller.bll.TripPlannerLogic
import com.example.tripplanner.R

class FotoViewHolder(itemView: View,var cardClick : () -> Unit, var silClick : (encodedImage : String) -> Unit) : RecyclerView.ViewHolder(itemView) {

    /** Define View Elements*/
    val imageView : ImageView
    val fotoEkleConstraintLayout: ConstraintLayout
    val ekliFotoConstraintLayout: ConstraintLayout
    val fotoEkleBtn : Button
    val fotoSilBtn : ImageButton

    init {
        imageView = itemView.findViewById(R.id.ivEklenenFotografZC)
        fotoEkleConstraintLayout = itemView.findViewById(R.id.cl_foto_ekle)
        ekliFotoConstraintLayout = itemView.findViewById(R.id.cl_ekli_foto)
        fotoEkleBtn = itemView.findViewById(R.id.btnFotografEkleZC)
        fotoSilBtn = itemView.findViewById(R.id.btnSilZC)
    }

    fun bindDataToViews(encodedImage: String, isLastItem: Boolean){

        if (encodedImage != "") {
            ekliFotoConstraintLayout.visibility = View.VISIBLE

            imageView.setImageBitmap(TripPlannerLogic.decodeBase64(encodedImage))

            if(!isLastItem){
                fotoEkleConstraintLayout.visibility = View.GONE
            }
        } else {
            ekliFotoConstraintLayout.visibility = View.GONE
        }

        fotoEkleBtn.setOnClickListener {
            cardClick()
        }

        fotoSilBtn.setOnClickListener {
            silClick(encodedImage)
        }


    }

}