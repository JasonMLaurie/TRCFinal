package com.example.tripplanner.view.adapters.foto

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.model.YerEntity
import java.util.*

class FotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /** Define View Elements*/
    val imageView : ImageView

    init {
        // TODO views and click event
        imageView = itemView.findViewById(R.id.ivEklenenFotografZC)
    }

    fun bindDataToViews(uri : Uri){

        imageView.setImageURI(uri)
        // TODO bind entity fields to view elements

    }

}