package com.example.tripplanner.Controller.dal

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class GezilecekOperation(context: Context) {

    var TripPlannerDatabase : SQLiteDatabase? = null
    var dbOpenHelper: DatabaseOpenHelper

    init {
        dbOpenHelper = DatabaseOpenHelper(context, "TripPlannerDB",null,1)
    }

    fun open(){
        // Open DB to write, there is a readable version too.
        TripPlannerDatabase = dbOpenHelper.writableDatabase
    }

    fun close(){
        // Closes DB
        if(TripPlannerDatabase != null && TripPlannerDatabase!!.isOpen){
            TripPlannerDatabase!!.close()
        }
    }

}