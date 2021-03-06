package com.example.tripplanner.controller.dal

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.tripplanner.model.ResimEntity
import com.example.tripplanner.model.YerEntity
import com.example.tripplanner.model.ZiyaretEntity

class TripPlannerOperation(context: Context) {

    var tripPlannerDatabase: SQLiteDatabase? = null
    var dbOpenHelper: DatabaseOpenHelper

    /** Constant Strings */
    val yerTableStr = "YerTable"
    val yerAdiStr = "YerAdi"
    val yerTanimStr = "KisaTanim"
    val yerAciklamaStr = "KisaAciklama"
    val yerOncelikStr = "Oncelik"
    val yerZiyaretStr = "Ziyaret"
    val ziyaretTableStr = "ZiyaretTable"
    val ziyaretTarihStr = "Tarih"
    val ziyaretAciklamaStr = "Aciklama"
    val yerStr = "Yer"
    val yerLongitudeStr = "Longitude"
    val yerLatitudeStr = "Latitude"
    val resimTableStr = "Resim"
    val resimBaseStr = "Base64"

    init {
        dbOpenHelper = DatabaseOpenHelper(context, "TripPlannerDB", null, 1)
    }

    fun openDB() {
        // Open DB to write
        tripPlannerDatabase = dbOpenHelper.writableDatabase
    }

    fun closeDB() {
        // Closes DB
        if (tripPlannerDatabase != null && tripPlannerDatabase!!.isOpen) {
            tripPlannerDatabase!!.close()
        }
    }

    // TumYerleriGetir Func
    /*

    @SuppressLint("Range")
    fun tumYerleriGetir(): ArrayList<YerEntity> {

        val yerList2Return: ArrayList<YerEntity> = arrayListOf()
        var yerEntity: YerEntity

        openDB()
        val dbObject = tripPlannerDatabase!!.rawQuery("SELECT * FROM $yerTableStr", null)

        if (dbObject.moveToFirst()) {
            do {
                yerEntity = YerEntity(0.0,0.0)
                yerEntity.id = dbObject.getInt(0)
                yerEntity.yerAdi = dbObject.getString(dbObject.getColumnIndex(yerAdiStr))
                yerEntity.kisaTanim = dbObject.getString(dbObject.getColumnIndex(yerTanimStr))
                yerEntity.kisaAciklama = dbObject.getString(dbObject.getColumnIndex(yerAciklamaStr))
                yerEntity.oncelik = dbObject.getString(dbObject.getColumnIndex(yerOncelikStr))
                yerEntity.ziyaretEdildi = dbObject.getInt(dbObject.getColumnIndex(yerZiyaretStr))
                yerEntity.latitude = dbObject.getDouble(dbObject.getColumnIndex(yerLatitudeStr))
                yerEntity.longitude = dbObject.getDouble(dbObject.getColumnIndex(yerLongitudeStr))
                yerList2Return.add(yerEntity)
            } while (dbObject.moveToNext())
        }
        dbObject.close()
        closeDB()

        return yerList2Return
    }
*/

    @SuppressLint("Range")
    fun gezilecekleriGetir() : ArrayList<YerEntity>{

        val yerList2Return: ArrayList<YerEntity> = arrayListOf()
        var yerEntity: YerEntity

        openDB()
        val dbObject = tripPlannerDatabase!!.rawQuery("SELECT * FROM $yerTableStr WHERE $yerZiyaretStr = 0", null)

        if (dbObject.moveToFirst()) {
            do {
                yerEntity = YerEntity(0.0,0.0)
                yerEntity.id = dbObject.getInt(0)
                yerEntity.yerAdi = dbObject.getString(dbObject.getColumnIndex(yerAdiStr))
                yerEntity.kisaTanim = dbObject.getString(dbObject.getColumnIndex(yerTanimStr))
                yerEntity.kisaAciklama = dbObject.getString(dbObject.getColumnIndex(yerAciklamaStr))
                yerEntity.oncelik = dbObject.getString(dbObject.getColumnIndex(yerOncelikStr))
                yerEntity.ziyaretEdildi = dbObject.getInt(dbObject.getColumnIndex(yerZiyaretStr))
                yerEntity.latitude = dbObject.getDouble(dbObject.getColumnIndex(yerLatitudeStr))
                yerEntity.longitude = dbObject.getDouble(dbObject.getColumnIndex(yerLongitudeStr))
                yerList2Return.add(yerEntity)
            } while (dbObject.moveToNext())
        }
        dbObject.close()
        closeDB()

        return yerList2Return
    }

    @SuppressLint("Range")
    fun yerGetir(locationPair : Pair<Double, Double>): YerEntity {
        val yerEntity = YerEntity(0.0,0.0)

        openDB()
        val dbObject =
            tripPlannerDatabase!!.rawQuery("SELECT * FROM $yerTableStr WHERE Longitude = ? AND Latitude = ?", arrayOf(locationPair.second.toString(),locationPair.first.toString()))

        if (dbObject.moveToFirst()) {
            do {
                yerEntity.id = dbObject.getInt(0)
                yerEntity.yerAdi = dbObject.getString(dbObject.getColumnIndex(yerAdiStr))
                yerEntity.kisaTanim = dbObject.getString(dbObject.getColumnIndex(yerTanimStr))
                yerEntity.kisaAciklama = dbObject.getString(dbObject.getColumnIndex(yerAciklamaStr))
                yerEntity.oncelik = dbObject.getString(dbObject.getColumnIndex(yerOncelikStr))
                yerEntity.ziyaretEdildi = dbObject.getInt(dbObject.getColumnIndex(yerZiyaretStr))
                yerEntity.latitude = dbObject.getDouble(dbObject.getColumnIndex(yerLatitudeStr))
                yerEntity.longitude = dbObject.getDouble(dbObject.getColumnIndex(yerLongitudeStr))
            } while (dbObject.moveToNext())
        }
        dbObject.close()
        closeDB()

        return yerEntity
    }

    fun yerEkle(yerEntity: YerEntity): Boolean {

        val cv = ContentValues()

        cv.put(yerAdiStr, yerEntity.yerAdi)
        cv.put(yerAciklamaStr, yerEntity.kisaAciklama)
        cv.put(yerTanimStr, yerEntity.kisaTanim)
        cv.put(yerOncelikStr, yerEntity.oncelik)
        cv.put(yerZiyaretStr, yerEntity.ziyaretEdildi)
        cv.put(yerLongitudeStr, yerEntity.longitude)
        cv.put(yerLatitudeStr, yerEntity.latitude)

        openDB()
        val effectedRowCount = tripPlannerDatabase!!.insert(yerTableStr, null, cv)
        closeDB()

        return effectedRowCount > 0
    }

    fun yerGuncelle(yerEntity: YerEntity): Boolean {

        val cv = ContentValues()

        cv.put(yerAdiStr, yerEntity.yerAdi)
        cv.put(yerAciklamaStr, yerEntity.kisaAciklama)
        cv.put(yerTanimStr, yerEntity.kisaTanim)
        cv.put(yerOncelikStr, yerEntity.oncelik)
        cv.put(yerZiyaretStr, yerEntity.ziyaretEdildi)
        cv.put(yerLongitudeStr, yerEntity.longitude)
        cv.put(yerLatitudeStr, yerEntity.latitude)

        openDB()
        val effectedRowCount = tripPlannerDatabase!!.update(yerTableStr, cv, "Id = ?", arrayOf(yerEntity.id.toString()))
        closeDB()

        return effectedRowCount > 0
    }


    @SuppressLint("Range")
    fun ziyaretleriGetir(yerEntity: YerEntity): ArrayList<ZiyaretEntity> {

        val ziyaretList2Return: ArrayList<ZiyaretEntity> = arrayListOf()
        var ziyaretEntity: ZiyaretEntity

        openDB()
        val dbObject = tripPlannerDatabase!!.rawQuery("SELECT * FROM $ziyaretTableStr WHERE Yer = ${yerEntity.id}", null)

        if (dbObject.moveToFirst()) {
            do {
                ziyaretEntity = ZiyaretEntity()
                ziyaretEntity.id = dbObject.getInt(0)
                ziyaretEntity.tarih =
                    dbObject.getString(dbObject.getColumnIndex(ziyaretTarihStr))
                ziyaretEntity.aciklama =
                    dbObject.getString(dbObject.getColumnIndex(ziyaretAciklamaStr))
                ziyaretEntity.yerId = dbObject.getInt(dbObject.getColumnIndex(yerStr))
                ziyaretList2Return.add(ziyaretEntity)
            } while (dbObject.moveToNext())
        }
        dbObject.close()
        closeDB()

        return ziyaretList2Return
    }

    @SuppressLint("Range")
    fun gezdiklerimiGetir() : ArrayList<YerEntity>{

        val yerList2Return: ArrayList<YerEntity> = arrayListOf()
        var yerEntity: YerEntity

        openDB()
        val dbObject = tripPlannerDatabase!!.rawQuery("SELECT * FROM $yerTableStr WHERE $yerZiyaretStr > 0", null)

        if (dbObject.moveToFirst()) {
            do {
                yerEntity = YerEntity(0.0,0.0)
                yerEntity.id = dbObject.getInt(0)
                yerEntity.yerAdi = dbObject.getString(dbObject.getColumnIndex(yerAdiStr))
                yerEntity.kisaTanim = dbObject.getString(dbObject.getColumnIndex(yerTanimStr))
                yerEntity.kisaAciklama = dbObject.getString(dbObject.getColumnIndex(yerAciklamaStr))
                yerEntity.oncelik = dbObject.getString(dbObject.getColumnIndex(yerOncelikStr))
                yerEntity.ziyaretEdildi = dbObject.getInt(dbObject.getColumnIndex(yerZiyaretStr))
                yerEntity.latitude = dbObject.getDouble(dbObject.getColumnIndex(yerLatitudeStr))
                yerEntity.longitude = dbObject.getDouble(dbObject.getColumnIndex(yerLongitudeStr))
                yerList2Return.add(yerEntity)
            } while (dbObject.moveToNext())
        }
        dbObject.close()
        closeDB()

        return yerList2Return
    }

    fun ziyaretEkle(ziyaretEntity: ZiyaretEntity): Boolean {

        val cv = ContentValues()
        cv.put(ziyaretTarihStr, ziyaretEntity.tarih)
        cv.put(ziyaretAciklamaStr, ziyaretEntity.aciklama)
        cv.put(yerStr, ziyaretEntity.yerId)


        openDB()
        val effectedRowCount = tripPlannerDatabase!!.insert(ziyaretTableStr, null, cv)
        closeDB()

        return effectedRowCount > 0

    }

    @SuppressLint("Range")
    fun tumZiyaretleriGetir(): ArrayList<ZiyaretEntity> {

        val ziyaretList2Return: ArrayList<ZiyaretEntity> = arrayListOf()
        var ziyaretEntity: ZiyaretEntity

        openDB()
        val dbObject = tripPlannerDatabase!!.rawQuery("SELECT * FROM $ziyaretTableStr", null)

        if (dbObject.moveToFirst()) {
            do {
                ziyaretEntity = ZiyaretEntity()
                // Get data from 0th column of selected row(outer).
                ziyaretEntity.id = dbObject.getInt(0)
                // Preferred method over getting data from index. Indexes may shift.
                ziyaretEntity.tarih = dbObject.getString(dbObject.getColumnIndex(ziyaretTarihStr))
                ziyaretEntity.aciklama = dbObject.getString(dbObject.getColumnIndex(ziyaretAciklamaStr))
                ziyaretEntity.yerId = dbObject.getInt(dbObject.getColumnIndex(yerStr))
                ziyaretList2Return.add(ziyaretEntity)
            } while (dbObject.moveToNext())
        }
        dbObject.close()
        closeDB()

        return ziyaretList2Return

    }

    fun fotoEkle(resimEntity: ResimEntity) : Boolean {

        val cv = ContentValues()
        cv.put(resimBaseStr, resimEntity.base64)
        cv.put(yerStr, resimEntity.yerId)
        cv.put(ziyaretTarihStr,resimEntity.tarih)


        openDB()
        val effectedRowCount = tripPlannerDatabase!!.insert(resimTableStr, null, cv)
        closeDB()

        return effectedRowCount > 0

    }

    @SuppressLint("Range")
    fun fotolarGetir(yerId: Int) : ArrayList<ResimEntity> {

        val resimList2Return: ArrayList<ResimEntity> = arrayListOf()
        var resimEntity : ResimEntity

        openDB()
        val dbObject = tripPlannerDatabase!!.rawQuery("SELECT * FROM $resimTableStr WHERE Yer = $yerId" , null)

        if (dbObject.moveToFirst()) {
            do {
                resimEntity = ResimEntity()
                resimEntity.id = dbObject.getInt(0)
                resimEntity.base64 = dbObject.getString(dbObject.getColumnIndex(resimBaseStr))
                resimEntity.yerId = dbObject.getInt(dbObject.getColumnIndex(yerStr))
                resimEntity.tarih=dbObject.getString(dbObject.getColumnIndex(ziyaretTarihStr))
                resimList2Return.add(resimEntity)
            } while (dbObject.moveToNext())
        }
        dbObject.close()
        closeDB()

        return resimList2Return
    }

    @SuppressLint("Range")
    fun fotoGetir(uri: String) : ResimEntity {

        var resimEntity = ResimEntity()

        openDB()
        val dbObject = tripPlannerDatabase!!.rawQuery("SELECT * FROM $resimTableStr WHERE Base64 = ?", arrayOf(uri))

        if (dbObject.moveToFirst()) {
            do {
                resimEntity = ResimEntity()
                resimEntity.id = dbObject.getInt(0)
                resimEntity.base64 = dbObject.getString(dbObject.getColumnIndex(resimBaseStr))
                resimEntity.yerId = dbObject.getInt(dbObject.getColumnIndex(yerStr))
                resimEntity.tarih=dbObject.getString(dbObject.getColumnIndex(ziyaretTarihStr))
            } while (dbObject.moveToNext())
        }
        dbObject.close()
        closeDB()

        return resimEntity
    }


    fun fotoSil(resimEntity: ResimEntity) : Boolean {

        openDB()
        val effectedRowCount = tripPlannerDatabase!!.delete(resimTableStr,"Id = ?",arrayOf(resimEntity.id.toString()))
        closeDB()

        return effectedRowCount>0
    }

}