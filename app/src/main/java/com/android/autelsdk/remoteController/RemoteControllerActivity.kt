package com.android.autelsdk.remoteController

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
import com.android.autelsdk.remoteController.fragments.AircraftStatusDirectCommandRCFragment
import com.android.autelsdk.remoteController.fragments.FlightControlParameterReadingRCFragment
import com.android.autelsdk.remoteController.fragments.InterfaceDebuggingRCFragment
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController


// On Activity Start First getCustomViewResId() is called and then onCreate() --- nothing else on Activity Creation
class RemoteControllerActivity : BaseActivity<AutelRemoteController>() {
    val TAG = RemoteControllerActivity::class.java.simpleName

    private val viewModel: RemoteControllerViewModel by viewModels()
    private lateinit var binding: ActivityRemoteControllerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, customViewResId)

        initUi()
        handleListeners()

        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, InterfaceDebuggingRCFragment())
            .commitNow()

    }

    private fun handleListeners() {

        binding.interfaceDebug.root.setOnClickListener { v->
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, InterfaceDebuggingRCFragment())
                .commitNow()
        }

        binding.aircraftStatus.root.setOnClickListener { v->
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AircraftStatusDirectCommandRCFragment())
                .commitNow()
        }

        binding.flightControl.root.setOnClickListener { v->
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, FlightControlParameterReadingRCFragment())
                .commitNow()
        }

//        binding.debugLog.root.setOnClickListener { v->
//            supportFragmentManager.beginTransaction()
//                .replace(binding.container.id, AircraftStatusDirectCommandRCFragment())
//                .commitNow()
//        }

    }

    override fun initController(product: BaseProduct?): AutelRemoteController? {
        if (product != null)
            return product.remoteController

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_remote_controller
    }

    override fun initUi() {
        handleListeners()
    }


}