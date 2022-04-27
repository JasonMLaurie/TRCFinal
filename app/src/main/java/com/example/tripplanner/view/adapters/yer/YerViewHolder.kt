package com.example.tripplanner.view.adapters.yer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.Controller.bll.TripPlannerLogic
import com.example.tripplanner.R
import com.example.tripplanner.model.YerEntity
import java.util.*

class YerViewHolder(itemView: View, glist:ArrayList<YerEntity>, itemClick:(position:Int,itemView:View)->Unit):RecyclerView.ViewHolder(itemView) {

    var tvYerAdı:TextView
    var tvYerKisaTanim:TextView
    var tvYerKisaAciklama:TextView
    var ivYerFotograf: ImageView
    var ivOncelik: ImageView

    init {
        tvYerAdı=itemView.findViewById(R.id.tvYerAdiGC)
        tvYerKisaTanim=itemView.findViewById(R.id.tvYerKisaTanimGC)
        tvYerKisaAciklama=itemView.findViewById(R.id.tvYerKisaAciklemaGC)
        ivYerFotograf=itemView.findViewById(R.id.ivYerFotografGC)
        ivOncelik=itemView.findViewById(R.id.ivOncelikGC)

        itemView.setOnClickListener { itemClick(adapterPosition,itemView) }
    }




    fun bindDataToViews(context: Context, item: YerEntity) {

        tvYerAdı.text = item.yerAdi
        tvYerKisaTanim.text = item.kisaTanim
        tvYerKisaAciklama.text = item.kisaAciklama

/*            val tempResimList = TripPlannerLogic.fotolarGetir(context, item.id)
            if(tempResimList.isNullOrEmpty()){
                ivYerFotograf.setImageResource(R.drawable.tempimage1)
            }else{
                ivYerFotograf.setImageURI(Uri.parse(TripPlannerLogic.fotolarGetir(context, item.id)[0].uri))
            }
        }*/


        //TODO setting image to ivYerFotograf

        /*
        //!!
        if (item.Oncelik=="Oncelik1")//yeşil
        { ivOncelik.setBackgroundColor(R.drawable.oncelik1_sekil) }
        else if (item.Oncelik=="Oncelik2")//mavi
        { ivOncelik.setBackgroundColor(R.drawable.oncelik2_sekil) }
        else if (item.Oncelik=="Oncelik3")//gri
        { ivOncelik.setBackgroundColor(R.drawable.oncelik3_sekil) }*/

    }
    }
