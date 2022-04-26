package com.example.tripplanner.view.adapters.ziyaret

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.model.ZiyaretEntity

class ZiyaretAdapter(var mContext: Context, var ziyaretList: ArrayList<ZiyaretEntity>) : RecyclerView.Adapter<ZiyaretViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZiyaretViewHolder {
        var layoutInflater = LayoutInflater.from(mContext)
        var cardView = layoutInflater.inflate(R.layout.ziyaret_gecmisi_card,parent,false)
        return ZiyaretViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ZiyaretViewHolder, position: Int) {
        holder.bindDataToViews(ziyaretEntity = ziyaretList[position])
    }

    override fun getItemCount(): Int {
        return ziyaretList.size
    }
}