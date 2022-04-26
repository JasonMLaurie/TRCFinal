package com.example.tripplanner.model

import com.example.tripplanner.R

data class Oncelik(var icon:Int,var oncelikDurumu:String)

object Oncelikler {
    private val icons = intArrayOf(R.drawable.oncelik1_sekil,
        R.drawable.oncelik2_sekil,
        R.drawable.oncelik3_sekil)

    private val oncelikDurumlari = arrayOf("oncelik1", "oncelik2", "oncelik3")

    var list: ArrayList<Oncelik>? = null
        get() {
            if (field != null)
                return field
            field = ArrayList()
            for (i in icons.indices) {
                val iconId = icons[i]
                val oncelikDurumu = oncelikDurumlari[i]

                val oncelik = Oncelik(iconId, oncelikDurumu)
                field!!.add(oncelik)
            }
            return field
        }
}