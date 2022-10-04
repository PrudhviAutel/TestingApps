package com.android.autelsdk.codec

import android.os.Bundle
import android.text.Spannable
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityCodecBinding
import com.android.autelsdk.databinding.ActivityFlyControllerBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.flyController.FlyControllerViewModel
import com.android.autelsdk.flyController.fragments.*
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.flycontroller.LedPilotLamp
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.video.AutelCodec
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Observer

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
