package com.android.autelsdk.FlyController

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding

class FlyControllerActivity : AppCompatActivity() {
    lateinit var binding : ActivityFlyControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_fly_controller)

        handleListeners()
    }

    private fun handleListeners() {
        
    }
}