package com.example.tripplanner.model

import java.util.*
import kotlin.properties.Delegates

class GezdiklerimEntity {

    var id by Delegates.notNull<Int>()
    var tarih: String? = null
    var aciklama: String? = null
    var yerId = 0

}