package com.example.tripplanner.view.adapters.foto

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.model.YerEntity
import java.util.*

class FotoViewHolder(itemView: View,var cardClick : () -> Unit) : RecyclerView.ViewHolder(itemView) {

    /** Define View Elements*/
    val imageView : ImageView
    val fotoEkleConstraintLayout: ConstraintLayout
    val ekliFotoConstraintLayout: ConstraintLayout
    val fotoEkleBtn : Button

    init {
        // TODO views and click event
        imageView = itemView.findViewById(R.id.ivEklenenFotografZC)
        fotoEkleConstraintLayout = itemView.findViewById(R.id.cl_foto_ekle)
        ekliFotoConstraintLayout = itemView.findViewById(R.id.cl_ekli_foto)
        fotoEkleBtn = itemView.findViewById(R.id.btnFotografEkleZC)

        itemView.setOnClickListener {
            //TODO nav to detail page
        }
    }

    fun bindDataToViews(uri : Uri, isLastItem: Boolean){

        if (uri != null && !uri.equals(Uri.EMPTY)) {
            ekliFotoConstraintLayout.visibility = View.VISIBLE

            imageView.setImageURI(uri)

            if(!isLastItem){
                fotoEkleConstraintLayout.visibility = View.GONE
            }
        } else {
            ekliFotoConstraintLayout.visibility = View.GONE
        }

        fotoEkleBtn.setOnClickListener {
            cardClick()
        }

        // TODO bind entity fields to view elements

    }

}