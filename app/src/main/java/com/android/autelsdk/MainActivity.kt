package com.android.autelsdk

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.autelsdk.RemoteController.RemoteControllerActivity
import com.android.autelsdk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        handleListeners()
    }

    private fun handleListeners() {

        binding.runRemoteControllerTests.setOnClickListener { v ->
            startActivity(Intent(this@MainActivity, RemoteControllerActivity::class.java))
        }

    }

}