package com.example.tripplanner.view.adapters.foto

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.model.YerEntity
import java.util.*

class FotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /** Define View Elements*/
    val imageView : ImageView
    val fotoEkleConstraintLayout: ConstraintLayout

    init {
        // TODO views and click event
        imageView = itemView.findViewById(R.id.ivEklenenFotografZC)
        fotoEkleConstraintLayout = itemView.findViewById(R.id.cl_foto_ekle)
    }

    fun bindDataToViews(uri : Uri, isLastItem: Boolean){

        imageView.setImageURI(uri)
        if(!isLastItem){
            fotoEkleConstraintLayout.visibility = View.GONE
        }
        // TODO bind entity fields to view elements

    }

}