package com.example.tripplanner.Controller.bll

import androidx.core.view.isVisible
import com.example.tripplanner.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class UILogic {
    companion object{
        fun ActivityMainBinding.hideTabLayout(boolean: Boolean){
            this.tabLayout.isVisible=!boolean
            this.fabYerEkle.isVisible=!boolean
        }
    }
}