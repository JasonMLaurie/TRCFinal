package com.example.tripplanner.Controller.dal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, ver: Int) : SQLiteOpenHelper(context,name,factory,ver) {

    override fun onCreate(p0: SQLiteDatabase) {
        val yerSorgu = "CREATE TABLE YerTable(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, YerAdi TEXT, KisaTanim TEXT, KisaAciklama TEXT, Oncelik TEXT, Ziyaret INTEGER, Longitude REAL, Latitude REAL)"
        val ziyaretSorgu =
            "CREATE TABLE ZiyaretTable(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Tarih TEXT, Aciklama TEXT, Yer INTEGER NOT NULL, FOREIGN KEY(Yer) REFERENCES Yer(Id))"
//        val bileşikResimSorgu = "CREATE TABLE Resim(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Uri TEXT, Yer INTEGER NOT NULL, FOREIGN KEY(Yer) REFERENCES Yer(Id))"
        val base64ResimSorgu = "CREATE TABLE Resim(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Base64 TEXT, Yer INTEGER NOT NULL, FOREIGN KEY(Yer) REFERENCES Yer(Id))"

        // TODO Use cursor.getLong() when receiving the date.

        p0.execSQL(yerSorgu)
        p0.execSQL(ziyaretSorgu)
//        p0.execSQL(bileşikResimSorgu)
        p0.execSQL(base64ResimSorgu)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}