package com.example.tripplanner.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ActivityMainBinding
import com.example.tripplanner.databinding.TabLayoutBinding
import com.example.tripplanner.view.fragments.GezdiklerimFragment
import com.example.tripplanner.view.fragments.GezilecekFragment
import com.example.tripplanner.view.fragments.YerEkleFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabYerEkle.setOnClickListener {
            fragmentDegistir(YerEkleFragment())
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
        tabSec(0) //tekrar aynı fragment oluşmasını engelliyoruz

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
            0 ->fragmentDegistir(GezilecekFragment())
            1 ->fragmentDegistir(GezdiklerimFragment())
        }
    }

    fun fragmentDegistir(fragment: Fragment){
        binding.tabLayout.isVisible=true
        binding.fabYerEkle.isVisible=true

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentContainerView, fragment)
        ft.commit()
    }



    override fun onBackPressed() {
        //todo değistir - geri gidince main activityi tekrar başlatıyor
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}