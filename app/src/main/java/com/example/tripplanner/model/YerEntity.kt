package com.example.tripplanner.model

import kotlin.properties.Delegates

class YerEntity {

    var id by Delegates.notNull<Int>()
    var yerAdi: String? = null
    var kisaTanim: String? = null
    var kisaAciklama: String? = null
    var oncelik: Int? = null

}