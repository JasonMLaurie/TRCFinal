package com.example.tripplanner.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.tripplanner.Controller.bll.CamMediaAccessLogic
import com.example.tripplanner.Controller.bll.UILogic
import com.example.tripplanner.Controller.bll.UILogic.Companion.hideTabLayout
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ActivityMainBinding
import com.example.tripplanner.databinding.TabLayoutBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : PermissionActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var navHostFragment : NavHostFragment

    private lateinit var tab:TabLayoutBinding

    override fun grantedFunc() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        //adding tabs
        binding.tabLayout.addTab(binding.tabLayout.newTab())
        binding.tabLayout.addTab(binding.tabLayout.newTab())

        tabOlustur()

        clickListeners()

        tabListener()
    }

    @SuppressLint("SetTextI18n")
    fun tabOlustur(){
        tab= TabLayoutBinding.inflate(layoutInflater)

        tab.tvbaslik.text="Gezilecek"
        tab.ivTabIcon.setImageResource(R.drawable.tab_select_gezilecek)
        binding.tabLayout.getTabAt(0)!!.setCustomView(tab.root)

        tab = TabLayoutBinding.inflate(layoutInflater)
        tab.tvbaslik.text="Gezdiklerim"
        tab.ivTabIcon.setImageResource(R.drawable.tab_select_gediklerim)
        binding.tabLayout.getTabAt(1)!!.setCustomView(tab.root)
    }

    fun tabSec(index:Int){
        when (index)
        {
            0 -> {
                Navigation.findNavController(this@MainActivity, R.id.fragmentContainerView).navigate(R.id.gezileceklerNavigation)
            }
            1 -> {
                Navigation.findNavController(this@MainActivity, R.id.fragmentContainerView).navigate(R.id.gezdiklerimNavigation)
            }
        }
    }

    private fun clickListeners(){
        binding.fabYerEkle.setOnClickListener {
            Navigation.findNavController(this@MainActivity, R.id.fragmentContainerView).navigate(R.id.yerEkleFragment)
        }
    }

    private fun tabListener(){
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSec(tab!!.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tabSec(tab!!.position)
            }
        })
    }

    override fun onBackPressed() {
        binding.hideTabLayout(false)
        // Backstack shows 0 even though backpress still follows stack all the way back.
        super.onBackPressed()
    }

}