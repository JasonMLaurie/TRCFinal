package com.example.tripplanner.model

import kotlin.properties.Delegates

class ResimEntity {

    var id by Delegates.notNull<Int>()
    var base64: String? = null
    var yerId by Delegates.notNull<Int>()
    var tarih:String?=null

}