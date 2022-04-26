package com.example.tripplanner.model

import kotlin.properties.Delegates

class ResimEntity {

    var id by Delegates.notNull<Int>()
    var uri: String? = null
    var yerId by Delegates.notNull<Int>()

}