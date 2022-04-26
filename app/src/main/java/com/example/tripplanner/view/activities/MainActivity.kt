package com.example.tripplanner.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tripplanner.R

class MainActivity : PermissionActivity() {

    override fun grantedFunc() { //TODO
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val intent = Intent(this, MapsActivity::class.java)
//        startActivity(intent)

    }
}