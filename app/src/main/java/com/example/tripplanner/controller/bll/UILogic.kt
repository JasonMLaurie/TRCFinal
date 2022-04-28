package com.example.tripplanner.controller.bll

import androidx.core.view.isVisible
import com.example.tripplanner.databinding.ActivityMainBinding

class UILogic {
    companion object{
        fun ActivityMainBinding.hideTabLayout(boolean: Boolean){
            this.tabLayout.isVisible=!boolean
            this.fabYerEkle.isVisible=!boolean
        }
    }
}