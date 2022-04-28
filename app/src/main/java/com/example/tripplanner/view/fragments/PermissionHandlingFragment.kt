package com.example.tripplanner.view.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


abstract class PermissionHandlingFragment : Fragment() {
    abstract fun  grantedFunc()
    var activityResultLauncher:ActivityResultLauncher<Array<String>>?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultLauncher=registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){result->
            var allAreGranted=true
            for (b in result.values){
                allAreGranted=allAreGranted && b
            }
            if (allAreGranted){
                grantedFunc()
            }else{
                Toast.makeText(requireContext(),"Permissions Not Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }


}