package com.example.tripplanner.view.adapters.yer

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.model.YerEntity
import java.util.*

class YerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    /** Define View Elements*/
    val emptyTv : TextView

    init {
        // TODO views and click event
        emptyTv = itemView.findViewById<TextView>(R.id.textKisaAciklama)

    }

    fun bindDataToViews(yerEntity: YerEntity, date: Date?){

        // TODO bind entity fields to view elements

    }
}