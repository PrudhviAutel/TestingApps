package com.android.autelsdk.battery

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.TestApplication
import com.android.autelsdk.battery.view.AirCraft_CommandStatusFragment
import com.android.autelsdk.battery.view.AutoTest_BatteryFragment
import com.android.autelsdk.battery.view.ManualIndividual_BatteryFragment
import com.android.autelsdk.battery.view.InterfaceTest_BatteryFragment
import com.android.autelsdk.databinding.ActivityBatteryBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.autel.common.product.AutelProductType
import com.autel.sdk.Autel
import com.autel.sdk.ProductConnectListener
import com.autel.sdk.battery.AutelBattery
import com.autel.sdk.product.BaseProduct
import com.autonavi.base.ae.gmap.GLEngineIDController.getController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.atomic.AtomicBoolean

class DFBatteryActivity : BaseActivity<AutelBattery>() {

    lateinit var binding: ActivityBatteryBinding
    lateinit var viewModel: BatteryViewModel

    override fun initController(product: BaseProduct?): AutelBattery {
        return product!!.battery
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_battery
    }

    override fun initUi() {
        handleListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        binding = DataBindingUtil.setContentView(this, customViewResId)
        viewModel = ViewModelProvider(this).get(BatteryViewModel::class.java)


        initUi()
        handleListeners()
        deselectAllTabs()

        viewModel.setController(mController)
    }

    private fun handleListeners() {

        binding.interfaceDebug.root.setOnClickListener { v ->
            deselectAllTabs()
            binding.interfaceDebug.optionParent.setBackgroundColor(
                ContextCompat.getColor(
                    this@DFBatteryActivity,
                    R.color.blue
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, InterfaceTest_BatteryFragment())
                .commitNow()
        }

        binding.aircraftStatus.root.setOnClickListener { v ->
            deselectAllTabs()
            binding.aircraftStatus.optionParent.setBackgroundColor(
                ContextCompat.getColor(
                    this@DFBatteryActivity,
                    R.color.blue
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AirCraft_CommandStatusFragment())
                .commitNow()
        }

        binding.flightControl.root.setOnClickListener { v ->
            deselectAllTabs()
            binding.flightControl.optionParent.setBackgroundColor(
                ContextCompat.getColor(
                    this@DFBatteryActivity,
                    R.color.blue
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, ManualIndividual_BatteryFragment())
                .commitNow()
        }

        binding.debugLog.root.setOnClickListener { v ->
            deselectAllTabs()
            binding.debugLog.optionParent.setBackgroundColor(
                ContextCompat.getColor(
                    this@DFBatteryActivity,
                    R.color.blue
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AutoTest_BatteryFragment())
                .commitNow()
        }

    }


    private fun deselectAllTabs() {
        binding.interfaceDebug.optionParent.setBackgroundColor(
            ContextCompat.getColor(
                this@DFBatteryActivity,
                R.color.white
            )
        )
        binding.aircraftStatus.optionParent.setBackgroundColor(
            ContextCompat.getColor(
                this@DFBatteryActivity,
                R.color.white
            )
        )
        binding.flightControl.optionParent.setBackgroundColor(
            ContextCompat.getColor(
                this@DFBatteryActivity,
                R.color.white
            )
        )
        binding.debugLog.optionParent.setBackgroundColor(
            ContextCompat.getColor(
                this@DFBatteryActivity,
                R.color.white
            )
        )
    }

    private var currentType = AutelProductType.UNKNOWN

    var hasInitProductListener = AtomicBoolean(false)
    private fun connectProduct() {
        Autel.setProductConnectListener(object : ProductConnectListener {
            override fun productConnected(product: BaseProduct) {
                Log.v("productType", "product " + product.type)
                currentType = product.type
                hasInitProductListener.compareAndSet(false, true)
                val previous: BaseProduct =
                    (applicationContext as TestApplication).currentProduct
                (applicationContext as TestApplication).currentProduct = product
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