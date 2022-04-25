package com.example.tripplanner.Controller.dal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, ver: Int) : SQLiteOpenHelper(context,name,factory,ver) {

    override fun onCreate(p0: SQLiteDatabase) {
        val yerSorgu = "CREATE TABLE Yer(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, YerAdi TEXT, KisaTanim TEXT, KisaAciklama TEXT)"
        val gezdiklerimSorgu = "CREATE TABLE Gezdiklerim(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Tarih Int, Aciklama TEXT, Yer INTEGER NOT NULL, FOREIGN KEY(Yer) REFERENCES Yer(Id))"
        // TODO Use cursor.getLong() when receiving the date.

        p0.execSQL(yerSorgu)
        p0.execSQL(gezdiklerimSorgu)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}