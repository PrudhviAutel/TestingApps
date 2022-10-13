package com.android.autelsdk.serialAidl

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityCodecBinding
import com.android.autelsdk.databinding.ActivitySerialAidlBinding
import com.android.autelsdk.flyController.fragments.InterfaceDebuggingCodecFragment
import com.autel.aidl.IHardwareManager

class SerialAidlActivity : AppCompatActivity() {
    lateinit var binding: ActivityCodecBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getCustomViewResId() )
        deselectAllTabs()
        initUi()
        handleListeners()
        binding.heading.setText("Serial Aidl Assistant")
        binding.interfaceDebugAc.title.setText("")
        binding.interfaceDebugAc.optionParent.setBackgroundColor(ContextCompat.getColor(this@SerialAidlActivity, R.color.blue))
        supportFragmentManager.beginTransaction()
                .replace(binding.container.id, InterfaceDebuggingCodecFragment())
                .commitNow()
    }

    private fun handleListeners() {
        TODO("Not yet implemented")
    }

    private fun initUi() {
        TODO("Not yet implemented")
    }

    private fun deselectAllTabs() {
        binding.interfaceDebugAc.optionParent.setBackgroundColor(ContextCompat.getColor(this@SerialAidlActivity, R.color.white))
        binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@SerialAidlActivity, R.color.white))
        binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@SerialAidlActivity, R.color.white))
        binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@SerialAidlActivity, R.color.white))
    }


    private fun getCustomViewResId(): Int {
        return R.layout.activity_serial_aidl
    }
}