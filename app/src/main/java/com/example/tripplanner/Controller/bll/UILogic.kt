package com.example.tripplanner.Controller.bll

import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class UILogic {
    companion object{
        fun TabLayout.hide(boolean: Boolean){
            this.isVisible=!boolean
        }
        fun FloatingActionButton.hide(boolean: Boolean){
            this.isVisible=!boolean
        }
    }
}