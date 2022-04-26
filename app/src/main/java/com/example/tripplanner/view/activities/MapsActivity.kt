package com.example.tripplanner.view.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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
    var locationManager:LocationManager?=null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager=getSystemService(LOCATION_SERVICE) as LocationManager
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.MapsActivity_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        PermissionLogic.locationPermissionControl(this,this)

        initializeMode()
        configureButton()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (!mode){
            mMap.setOnMapLongClickListener {
                mMap.clear()
                locationPair= Pair("Konum",it)
                mMap.addMarker(MarkerOptions().position(locationPair!!.second).title(locationPair!!.first))
            }
        }

    }

    private fun initializeMode(){
        mode= Intent().getBooleanExtra("mode",false)
    }
    @SuppressLint("MissingPermission")
    override fun grantedFunc() {
        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 0.1f,locationListener)
    }
    var locationListener = object : LocationListener{
        override fun onLocationChanged(p0: Location) {
            mMap.clear()
            Toast.makeText(this@MapsActivity,"Konum degisti",Toast.LENGTH_SHORT).show()
            locationPair= Pair("Konum", LatLng(p0.latitude,p0.longitude))
            mMap.addMarker(MarkerOptions().position(locationPair!!.second))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationPair!!.second))
            locationManager!!.removeUpdates(this)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
            //TODO konum kapalıyken crash
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

    }
    private fun configureButton(){
        if(mode){
            //Konuma gidis
        }else{
            binding.btnKonumKaydet.setOnClickListener {
                val intent=Intent()
                intent.putExtra("location",locationPair)
                setResult(RESULT_OK,intent)
                finish()
            }
        }
    }


}