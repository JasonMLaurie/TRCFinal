package com.example.tripplanner.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.tripplanner.R


class CustomPagerAdapter(private val context: Context, fotograflar: IntArray,tarihler:Array<String>) :
    PagerAdapter() {
    var mLayoutInflater: LayoutInflater
    private val vpFotografListe: IntArray
    private val vpTarihListe: Array<String>



    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false)
        val ivFotograf = itemView.findViewById<View>(R.id.ivSliderFotograf) as ImageView
        val tvTarih = itemView.findViewById<View>(R.id.tvSliderTarih) as TextView
        ivFotograf.setImageResource(vpFotografListe[position])
        tvTarih.setText(vpTarihListe[position])


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

    init {
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vpFotografListe = fotograflar
        vpTarihListe = tarihler
    }
}