package com.example.tripplanner.view.adapters.custom

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.tripplanner.R
import com.example.tripplanner.model.Oncelik

class SpinnerAdapter(context: Context,oncelikList: List<Oncelik>):ArrayAdapter<Oncelik>(context,0,oncelikList){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    @SuppressLint("ResourceType")
    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View{
        val oncelik=getItem(position)
        val view=convertView?:LayoutInflater.from(context).inflate(R.layout.oncelik_spinner_item,parent,false)
        view.findViewById<ImageView>(R.id.ivOncelikDurumu).setImageResource(oncelik!!.icon)

        return view
    }


}