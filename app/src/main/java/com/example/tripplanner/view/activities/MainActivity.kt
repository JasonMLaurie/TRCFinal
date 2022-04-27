package com.example.tripplanner.view.activities

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ActivityMainBinding
import com.example.tripplanner.databinding.TabLayoutBinding
import com.example.tripplanner.view.fragments.*
import com.google.android.material.tabs.TabLayout

class MainActivity : PermissionActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var navHostFragment : NavHostFragment


    override fun grantedFunc() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment


        //adding tabs
        binding.tabLayout.addTab(binding.tabLayout.newTab())
        binding.tabLayout.addTab(binding.tabLayout.newTab())

        tabOlustur()

        clickListeners()

        tabListener()

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
        val primeFragmentName = supportFragmentManager.primaryNavigationFragment!!.childFragmentManager.fragments[0].javaClass.name
        when (index)
        {
            0 -> {
                val navDir2GezilecekFragment : NavDirections
                if(primeFragmentName.equals("com.example.tripplanner.view.fragments.GezdiklerimFragment")){
                    navDir2GezilecekFragment = GezdiklerimFragmentDirections.actionGezdiklerimFragmentToGezilecekFragment2()
                }else{
                    navDir2GezilecekFragment = DetayFragmentDirections.actionDetayFragmentToGezilecekFragment2()
                }
                fragmentDegistir(navDir2GezilecekFragment)
            }
            1 -> {
                val navDir2GezdiklerimFragment : NavDirections
                if(primeFragmentName.equals("com.example.tripplanner.view.fragments.GezilecekFragment")){
                    navDir2GezdiklerimFragment = GezilecekFragmentDirections.actionGezilecekFragment2ToGezdiklerimFragment()
                }else{
                    navDir2GezdiklerimFragment = DetayFragmentDirections.actionDetayFragmentToGezdiklerimFragment()
                }
                fragmentDegistir(navDir2GezdiklerimFragment)
            }
        }
    }

    fun fragmentDegistir(navDirObject : NavDirections){
        binding.tabLayout.isVisible=true
        binding.fabYerEkle.isVisible=true

        val navController = navHostFragment.navController
        navController.navigate(navDirObject)

        /*supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .setPrimaryNavigationFragment(fragment)
            .commit()*/
    }

    private fun clickListeners(){

        binding.fabYerEkle.setOnClickListener {

            val navDir2YerEkleFragment : NavDirections

            val primeFragmentName = supportFragmentManager.primaryNavigationFragment!!.childFragmentManager.fragments[0].javaClass.name
            when(primeFragmentName){
                "com.example.tripplanner.view.fragments.GezdiklerimFragment" -> {
                    navDir2YerEkleFragment = GezdiklerimFragmentDirections.actionGezdiklerimFragmentToYerEkleFragment()

                }
                "com.example.tripplanner.view.fragments.GezilecekFragment" -> {
                    navDir2YerEkleFragment = GezilecekFragmentDirections.actionGezilecekFragment2ToYerEkleFragment()

                }
                "com.example.tripplanner.view.fragments.DetayFragment" -> {
                    navDir2YerEkleFragment = DetayFragmentDirections.actionDetayFragmentToYerEkleFragment()
                }
                else ->
                    navDir2YerEkleFragment = GezilecekFragmentDirections.actionGezilecekFragment2ToYerEkleFragment()
            }

            fragmentDegistir(navDir2YerEkleFragment)
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

            }

        })
    }

    override fun onBackPressed() {
        if(!binding.tabLayout.isVisible){
            binding.tabLayout.isVisible=true
            binding.fabYerEkle.isVisible=true
        }
        super.onBackPressed()
    }

    override fun onResume() {
        binding.tabLayout.isVisible=true
        binding.fabYerEkle.isVisible=true
        super.onResume()
    }

}