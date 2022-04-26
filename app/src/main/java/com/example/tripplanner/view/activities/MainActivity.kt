package com.example.tripplanner.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ActivityMainBinding
import com.example.tripplanner.databinding.TabLayoutBinding
import com.example.tripplanner.view.fragments.*
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabYerEkle.setOnClickListener {
            val navDir2YerEkleFragment = GezilecekFragmentDirections.actionGezilecekFragment2ToYerEkleFragment()
            fragmentDegistir(navDir2YerEkleFragment)
        }

        //adding tabs
        binding.tabLayout.addTab(binding.tabLayout.newTab())
        binding.tabLayout.addTab(binding.tabLayout.newTab())

        tabOlustur()

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSec(tab!!.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
//        tabSec(0) //tekrar aynı fragment oluşmasını engelliyoruz

    }

    @SuppressLint("SetTextI18n")
    fun tabOlustur(){
        var tab= TabLayoutBinding.inflate(layoutInflater)
        tab.tvbaslik.text="Gezilecek"
        tab.ivTabIcon.setImageResource(R.drawable.gezilecekler_icon)
        binding.tabLayout.getTabAt(0)!!.setCustomView(tab.root)

        tab = TabLayoutBinding.inflate(layoutInflater)
        tab.tvbaslik.text="Gezdiklerim"
        tab.ivTabIcon.setImageResource(R.drawable.gezdiklerim_icon)
        binding.tabLayout.getTabAt(1)!!.setCustomView(tab.root)
    }

    fun tabSec(index:Int){
        when (index)
        {
            0 -> {
                val navDir2GezilecekFragment = GezdiklerimFragmentDirections.actionGezdiklerimFragmentToGezilecekFragment2()
                fragmentDegistir(navDir2GezilecekFragment)
            }
            1 -> {
                val navDir2GezdiklerimFragment = GezilecekFragmentDirections.actionGezilecekFragment2ToGezdiklerimFragment()
                fragmentDegistir(navDir2GezdiklerimFragment)
            }
        }
    }

    fun fragmentDegistir(navDirObject : NavDirections){
        binding.tabLayout.isVisible=true
        binding.fabYerEkle.isVisible=true

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(navDirObject)

        /*supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .setPrimaryNavigationFragment(fragment)
            .commit()*/
    }



    override fun onBackPressed() {
        super.onBackPressed()
        //todo değistir - geri gidince main activityi tekrar başlatıyor
//        val intent= Intent(this,MainActivity::class.java)
//        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        binding.tabLayout.isVisible=true
        binding.fabYerEkle.isVisible=true
    }


}