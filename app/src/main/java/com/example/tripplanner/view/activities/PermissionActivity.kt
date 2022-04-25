package com.example.tripplanner.view.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class PermissionActivity:AppCompatActivity() {



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var allGranted=true
        grantResults.forEach {
            if (it!= PackageManager.PERMISSION_GRANTED){
                allGranted=false
                return@forEach
            }
        }
        if (allGranted){
            Toast.makeText(this,"Permissions Granted", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(this,"Permissions Not Granted", Toast.LENGTH_SHORT).show()

        }
    }
}