package com.android.autelsdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.android.autelsdk.databinding.ActivityMain2Binding
import com.android.autelsdk.databinding.ActivityMainBinding
import com.android.autelsdk.flyController.FlyControllerRepositoryImpl
import com.android.autelsdk.remoteController.RemoteControllerActivity
//import com.example.myapplicationdragonfish.databinding.ActivityMainBinding
import com.example.myapplicationdragonfish.viewmodel.MainViewModel

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, viewModel.listOfSdk)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerSdkList.adapter = adapter
        binding.spinnerSdkList.setSelection(0)

        binding.buttonTest.setOnClickListener(View.OnClickListener {
            //intent = Intent(this, RemoteControllerActivity::class.java)
            intent.putExtra("SdkName", binding.spinnerSdkList.selectedItem.toString())
            startActivity(intent)
        })


    }
}