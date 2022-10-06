package com.android.autelsdk.codec

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.codec.fragments.FlightControlParameterReadingCodecFragment
import com.android.autelsdk.databinding.ActivityCodecBinding
import com.android.autelsdk.flyController.fragments.AircraftStatusDirectCommandCodecFragment
import com.android.autelsdk.flyController.fragments.DebugLogCodecFragment
import com.android.autelsdk.flyController.fragments.InterfaceDebuggingCodecFragment
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.video.AutelCodec

class CodecActivity : BaseActivity<AutelCodec>() {
    lateinit var binding: ActivityCodecBinding
    private val viewModel: CodecViewModel by viewModels()



    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, customViewResId)
        deselectAllTabs()
        initUi()
        handleListeners()
        binding.interfaceDebugAc.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.blue))
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, InterfaceDebuggingCodecFragment())
            .commitNow()

    }

    private fun deselectAllTabs() {
        binding.interfaceDebugAc.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.white))
        binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.white))
        binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.white))
        binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.white))
    }

    private fun handleListeners(){
        binding.interfaceDebugAc.root.setOnClickListener { v->
            deselectAllTabs()
            binding.interfaceDebugAc.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, InterfaceDebuggingCodecFragment())
                .commitNow()
        }

        binding.aircraftStatus.root.setOnClickListener { v->
            deselectAllTabs()
            binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AircraftStatusDirectCommandCodecFragment())
                .commitNow()
        }

        binding.flightControl.root.setOnClickListener { v->
            deselectAllTabs()
            binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, FlightControlParameterReadingCodecFragment())
                .commitNow()
        }

        binding.debugLog.root.setOnClickListener { v->
            deselectAllTabs()
            binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@CodecActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DebugLogCodecFragment())
                .commitNow()
        }
    }

    override fun initController(product: BaseProduct?): AutelCodec? {
        if (product != null)
            return product!!.codec

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_codec

    }

    override fun initUi() {

    }


    }
