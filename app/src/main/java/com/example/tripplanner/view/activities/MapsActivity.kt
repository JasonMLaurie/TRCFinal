package com.example.tripplanner.view.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.tripplanner.R
import com.example.tripplanner.bll.PermissionLogic
import com.example.tripplanner.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : PermissionActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var mode=false  //false-> Konum secimi, true->Konuma git
    val reqCodeLocations=0
    var locationPair:Pair<String,LatLng>?=null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.MapsActivity_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        PermissionLogic.locationPermissionControl(this,this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        locationPair= Pair("Konum",sydney)

        if (!mode){
            mMap.setOnMapLongClickListener {
                locationPair= Pair("Konum",it)
                mMap.addMarker(MarkerOptions().position(locationPair!!.second).title(locationPair!!.first))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(locationPair!!.second))
            }
        }

    }

    private fun initializeMode(){
        mode= Intent().getBooleanExtra("mode",false)
    }


}