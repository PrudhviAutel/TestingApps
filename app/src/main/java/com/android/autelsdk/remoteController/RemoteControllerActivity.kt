package com.android.autelsdk.remoteController

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.TestApplication
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.flyController.fragments.AircraftStatusDirectCommandFCFragment
import com.android.autelsdk.flyController.fragments.FlightControlParameterReadingFCFragment
import com.android.autelsdk.remoteController.fragments.AircraftStatusDirectCommandRCFragment
import com.android.autelsdk.remoteController.fragments.DebugLogRCFragment
import com.android.autelsdk.remoteController.fragments.FlightControlParameterReadingRCFragment
import com.android.autelsdk.remoteController.fragments.InterfaceDebuggingRCFragment
import com.autel.common.product.AutelProductType
import com.autel.sdk.Autel
import com.autel.sdk.ProductConnectListener
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.atomic.AtomicBoolean


// On Activity Start First getCustomViewResId() is called and then onCreate() --- nothing else on Activity Creation
class RemoteControllerActivity : BaseActivity<AutelRemoteController>() {
    val TAG = RemoteControllerActivity::class.java.simpleName
    var hasInitProductListener = AtomicBoolean(false)
    private var currentType = AutelProductType.UNKNOWN

    private val viewModel: RemoteControllerViewModel by viewModels()
    private lateinit var binding: ActivityRemoteControllerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        binding = DataBindingUtil.setContentView(this, customViewResId)

        initUi()
        handleListeners()
        deselectAllTabs()

        binding.interfaceDebug.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.blue))
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, InterfaceDebuggingRCFragment())
            .commitNow()

    }

    private fun handleListeners() {

        binding.interfaceDebug.root.setOnClickListener { v->
            deselectAllTabs()
            binding.interfaceDebug.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, InterfaceDebuggingRCFragment())
                .commitNow()
        }

        binding.aircraftStatus.root.setOnClickListener { v->
            deselectAllTabs()
            binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AircraftStatusDirectCommandRCFragment())
                .commitNow()
        }

        binding.flightControl.root.setOnClickListener { v->
            deselectAllTabs()
            binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, FlightControlParameterReadingRCFragment())
                .commitNow()
        }

        binding.debugLog.root.setOnClickListener { v->
            deselectAllTabs()
            binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DebugLogRCFragment())
                .commitNow()
        }

    }

    private fun deselectAllTabs() {
        binding.interfaceDebug.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.white))
        binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.white))
        binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.white))
        binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@RemoteControllerActivity, R.color.white))
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

    private fun connectProduct() {
        Log.i("kkkkkkk","Launched Connect Product Event")
        Autel.setProductConnectListener(object : ProductConnectListener {
            override fun productConnected(product: BaseProduct) {
                Log.v("productType", "product " + product.type)
                currentType = product.type
                hasInitProductListener.compareAndSet(false, true)
                val previous: BaseProduct = (getApplicationContext() as TestApplication).getCurrentProduct()
                (getApplicationContext() as TestApplication).setCurrentProduct(product)
                viewModel.setCurrentProduct(product)
            }

            override fun productDisconnected() {
                Log.v("productType", "productDisconnected ")
                currentType = AutelProductType.UNKNOWN
                viewModel.setCurrentProduct(null)
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProductConnectEvent(event: ProductConnectEvent?) {
        if (event != null) {
            connectProduct()
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}