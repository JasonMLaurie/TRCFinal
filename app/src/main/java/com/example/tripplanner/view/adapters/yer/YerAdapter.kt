package com.example.tripplanner.view.adapters.yer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.model.YerEntity
import java.util.*
import kotlin.collections.ArrayList

class YerAdapter(var mContext: Context,var yerList: ArrayList<YerEntity>, var itemClick:(position:Int, itemView: View)->Unit ) : RecyclerView.Adapter<YerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YerViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        // TODO Card Layout
        val cardView = layoutInflater.inflate(R.layout.fragment_gezilecek, parent, false)
        return YerViewHolder(cardView,yerList, itemClick)
    }

    override fun onBindViewHolder(holder: YerViewHolder, position: Int) {
        holder.bindDataToViews(mContext ,yerList[position])
    }

    override fun getItemCount(): Int {
        return yerList.size
    }
}