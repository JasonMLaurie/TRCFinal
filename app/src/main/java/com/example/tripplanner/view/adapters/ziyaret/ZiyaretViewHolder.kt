package com.example.tripplanner.view.adapters.ziyaret

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.model.ZiyaretEntity

class ZiyaretViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val tarihTV : TextView
    val aciklamaTV : TextView


    init {
        tarihTV = itemView.findViewById(R.id.tvTarihCardZG)
        aciklamaTV = itemView.findViewById(R.id.tvAciklamaCardZg)

    }

    fun bindDataToViews(ziyaretEntity: ZiyaretEntity){

        val yer=YerEntity(0.0,0.0)
        yer.id=ziyaretEntity.yerId

        tarihTV.text = ziyaretEntity.tarih
        aciklamaTV.text = ziyaretEntity.aciklama


    }
}