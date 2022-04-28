package com.example.tripplanner.view.adapters.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.tripplanner.controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.model.ResimEntity


class CustomPagerAdapter(context: Context, fotograflar: ArrayList<ResimEntity>, resimClick:(position:Int)->Unit) :
    PagerAdapter() {
    var mLayoutInflater: LayoutInflater
    private val vpFotografListe: ArrayList<ResimEntity>
    val resimaClick:(position:Int)->Unit


    init {
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vpFotografListe = fotograflar
        resimaClick=resimClick

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false)
        val ivFotograf = itemView.findViewById<View>(R.id.ivSliderFotograf) as ImageView
        val tvTarih = itemView.findViewById<View>(R.id.tvSliderTarih) as TextView

        ivFotograf.setImageBitmap(TripPlannerLogic.decodeBase64(vpFotografListe[position].base64!!))
        tvTarih.text = vpFotografListe[position].tarih

        itemView.setOnClickListener {
            resimaClick(position)
        }


        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return vpFotografListe.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }


}

