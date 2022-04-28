package com.example.tripplanner.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.tripplanner.R
import com.example.tripplanner.controller.bll.PermissionLogic
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
    private var mode =
        false  //Ekleden gelirsen->false-> Konum secimi, Detaydan gelirsen->true->Konuma git
    val reqCodeLocations = 0
    var locationPair: Pair<String, LatLng>? = null
    var locationManager: LocationManager? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeMode()

        binding.btnBackM.setOnClickListener {
            onBackPressed()
        }
        binding.tvToolText2.setText(intent.getStringExtra("yer_adi"))

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.MapsActivity_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        configureButton()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (!mode) { // Yer Ekle kismindan gelirsen
            PermissionLogic.locationPermissionControl(this, this)
            mMap.setOnMapLongClickListener {
                mMap.clear()
                locationPair = Pair("Konum", it)
                mMap.addMarker(
                    MarkerOptions().position(locationPair!!.second).title(locationPair!!.first)
                )
            }
        }
        if (mode) {// Detaydan gelirsen
            mMap.addMarker(MarkerOptions().position(locationPair!!.second))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationPair!!.second))
        }

    }

    private fun initializeMode() {
        mode = intent.getBooleanExtra("mode", false)
        if (!mode) {//Yer Ekle kismindan gelirsen
        } else {//Detay kismindan gelirsen
            locationPair = Pair(
                "Konum",
                LatLng(
                    intent.getDoubleExtra("Latitude", 40.0),
                    intent.getDoubleExtra("Longitude", 40.0)
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun configureButton() {
        if (mode) {//Detay kismindan gelirsen
            //Konuma gidis
            binding.btnKonumKaydet.text = "Git"
            binding.btnKonumKaydet.setOnClickListener {
                val gmmIntentUri = Uri.parse(
                    "google.navigation:q=${
                        locationPair!!.second.latitude.toString()
                            .dropLast(locationPair!!.second.latitude.toString().length - 5)
                    },${
                        locationPair!!.second.longitude.toString()
                            .dropLast(locationPair!!.second.latitude.toString().length - 5)
                    }"
                )
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }

        } else {//Yer ekle kismindan gelirsen
            binding.btnKonumKaydet.setOnClickListener {
                val intent = Intent()
                val bundle = Bundle()
                bundle.putDouble("Latitude", locationPair!!.second.latitude)
                bundle.putDouble("Longitude", locationPair!!.second.longitude)
                intent.putExtras(bundle)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun grantedFunc() {
        if (!mode) {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                0.1f,
                locationListener
            )
        }
    }

    var locationListener = object : LocationListener {

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
        }

        override fun onLocationChanged(p0: Location) {
            mMap.clear()
            locationPair = Pair("Konum", LatLng(p0.latitude, p0.longitude))
            mMap.addMarker(MarkerOptions().position(locationPair!!.second))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationPair!!.second))
            locationManager!!.removeUpdates(this)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager?.removeUpdates(locationListener)
    }
}