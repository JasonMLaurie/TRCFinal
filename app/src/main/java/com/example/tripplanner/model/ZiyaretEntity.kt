package com.example.tripplanner.model

import java.util.*
import kotlin.properties.Delegates

class ZiyaretEntity {

    var id by Delegates.notNull<Int>()
    var tarih: String? = null
    var aciklama: String? = null
    var yerId by Delegates.notNull<Int>()
    // TODO Test

}