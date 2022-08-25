package com.android.autelsdk.RemoteController

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding

class RemoteControllerActivity : AppCompatActivity() {
    lateinit var binding : ActivityRemoteControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_remote_controller)

        handleListeners()
    }

    private fun handleListeners() {



    }




}