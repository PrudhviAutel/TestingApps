package com.android.autelsdk.dsp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.TestApplication
import com.android.autelsdk.databinding.ActivityDspBinding
import com.android.autelsdk.dsp.fragments.FlightControlParameterReadingDspFragment
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.gimbal.fragments.AircraftStatusDirectCommandDspFragment
import com.android.autelsdk.gimbal.fragments.DebugLogDspFragment
import com.android.autelsdk.gimbal.fragments.InterfaceDebuggingDspFragment
import com.autel.common.product.AutelProductType
import com.autel.sdk.Autel
import com.autel.sdk.ProductConnectListener
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.product.CruiserAircraft
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.atomic.AtomicBoolean


// On Activity Start First getCustomViewResId() is called and then onCreate() --- nothing else on Activity Creation
class DspActivity : BaseActivity<AutelGimbal>() {
    val TAG = DspActivity::class.java.simpleName
    var hasInitProductListener = AtomicBoolean(false)
    private var currentType = AutelProductType.UNKNOWN

    private val viewModel: DspViewModel by viewModels()
    private lateinit var binding: ActivityDspBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        binding = DataBindingUtil.setContentView(this, customViewResId)

        initUi()
        handleListeners()
        deselectAllTabs()

        binding.interfaceDebug.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.blue))
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, InterfaceDebuggingDspFragment())
            .commitNow()

    }

    private fun handleListeners() {

        binding.interfaceDebug.root.setOnClickListener { v->
            deselectAllTabs()
            binding.interfaceDebug.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, InterfaceDebuggingDspFragment())
                .commitNow()
        }

        binding.aircraftStatus.root.setOnClickListener { v->
            deselectAllTabs()
            binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AircraftStatusDirectCommandDspFragment())
                .commitNow()
        }

        binding.flightControl.root.setOnClickListener { v->
            deselectAllTabs()
            binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, FlightControlParameterReadingDspFragment())
                .commitNow()
        }

        binding.debugLog.root.setOnClickListener { v->
            deselectAllTabs()
            binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DebugLogDspFragment())
                .commitNow()
        }

    }

    private fun deselectAllTabs() {
        binding.interfaceDebug.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.white))
        binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.white))
        binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.white))
        binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@DspActivity, R.color.white))
    }

    override fun initController(product: BaseProduct?): AutelGimbal? {
        val cruiserGimbalController = (product as CruiserAircraft).gimbal
        viewModel.setController(cruiserGimbalController)
        return product.gimbal
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_gimbal
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