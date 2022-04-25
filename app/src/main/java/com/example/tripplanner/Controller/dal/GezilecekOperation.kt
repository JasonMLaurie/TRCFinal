package com.example.tripplanner.Controller.dal

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tripplanner.model.YerEntity

class GezilecekOperation(context: Context) {

    var tripPlannerDatabase : SQLiteDatabase? = null
    var dbOpenHelper: DatabaseOpenHelper

    /** Constant Strings */
    val yerTableStr = "Yer"
    val yerAdiStr = "YerAdi"
    val yerTanimStr = "KisaTanim"
    val yerAciklamaStr = "KisaAciklama"

    init {
        dbOpenHelper = DatabaseOpenHelper(context, "TripPlannerDB",null,1)
    }

    fun openDB(){
        // Open DB to write, there is a readable version too.
        tripPlannerDatabase = dbOpenHelper.writableDatabase
    }

    fun closeDB(){
        // Closes DB
        if(tripPlannerDatabase != null && tripPlannerDatabase!!.isOpen){
            tripPlannerDatabase!!.close()
        }
    }

    fun yerEkle(yerEntity: YerEntity) : Boolean{

        val cv = ContentValues()

        cv.put(yerAdiStr, yerEntity.yerAdi)
        cv.put(yerAciklamaStr, yerEntity.kisaAciklama)
        cv.put(yerTanimStr, yerEntity.kisaTanim)

        openDB()
        val effectedRowCount = tripPlannerDatabase!!.insert(yerTableStr, null, cv)
        closeDB()

        return effectedRowCount>0
    }


    fun getGezilecekYer(): Cursor {
        val sorgu = "SELECT * FROM Yer"
        return tripPlannerDatabase!!.rawQuery(sorgu,null)
    }

    @SuppressLint("Range")
    fun getTumGezilecekYer():ArrayList<YerEntity>{
        val gezilecekListe=ArrayList<YerEntity>()
        var yer : YerEntity
        openDB()
        var cursor: Cursor =getGezilecekYer()
        if (cursor.moveToFirst()){
            do {
                yer= YerEntity()
                yer.id=cursor.getInt(0)
                yer.yerAdi=cursor.getString(cursor.getColumnIndex(yerAdiStr))
                yer.kisaTanim=cursor.getString(cursor.getColumnIndex(yerTanimStr))
                yer.kisaAciklama=cursor.getString(cursor.getColumnIndex(yerAciklamaStr))

                gezilecekListe.add(yer)
            }while (cursor.moveToNext())
        }
        closeDB()
        return gezilecekListe
    }
}