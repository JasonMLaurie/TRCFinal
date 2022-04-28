package com.example.tripplanner.view.adapters.foto

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R

class FotoAdapter(var mContext : Context, var resimUriList : ArrayList<String>,var cardClick : () -> Unit, var silClick : (encodedImage : String) -> Unit) : RecyclerView.Adapter<FotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotoViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val cardView = layoutInflater.inflate(R.layout.ziyaret_fotograf_card, parent, false)
        return FotoViewHolder(cardView, cardClick, silClick)
    }

    override fun onBindViewHolder(holder: FotoViewHolder, position: Int) {
            holder.bindDataToViews(resimUriList[position], position==(resimUriList.size-1))
    }

    override fun getItemCount(): Int {
        return resimUriList.size
    }

}