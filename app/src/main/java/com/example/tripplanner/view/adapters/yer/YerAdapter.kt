package com.example.tripplanner.view.adapters.yer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.model.YerEntity
import kotlin.collections.ArrayList

class YerAdapter(val context: Context, var gezilecekListe:ArrayList<YerEntity>, val itemClick:(position:Int)->Unit):RecyclerView.Adapter<YerViewHolder>() {
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YerViewHolder {
         val view = LayoutInflater.from(context).inflate(R.layout.gezilecekler_card,parent,false)
         return YerViewHolder(view,gezilecekListe,itemClick)
     }

    override fun onBindViewHolder(holder: YerViewHolder, position: Int) {
        holder.bindDataToViews(context,gezilecekListe.get(position))
    }

    override fun getItemCount(): Int {
        return gezilecekListe.size
    }
}