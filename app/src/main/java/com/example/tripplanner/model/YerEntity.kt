package com.example.tripplanner.model

import java.io.Serializable
import kotlin.properties.Delegates

class YerEntity(
    var latitude : Double,
    var longitude : Double
) : Serializable{

    var id =0
    var yerAdi: String? = null
    var kisaTanim: String? = null
    var kisaAciklama: String? = null
    var oncelik: String? = null
    var ziyaretEdildi : Int = 0


}