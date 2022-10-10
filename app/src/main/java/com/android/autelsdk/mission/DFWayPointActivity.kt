package com.android.autelsdk.mission

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.autelsdk.R
import com.android.autelsdk.TestApplication
import com.android.autelsdk.databinding.ActivityDfwayPointBinding
import com.android.autelsdk.mission.view.AirCraftMission_CommandStatusFragment
import com.android.autelsdk.mission.view.AutoTest_MissionFragment
import com.android.autelsdk.mission.view.InterfaceTest_MissionFragment
import com.android.autelsdk.mission.view.ManualIndividual_MissionFragment
import com.autel.common.product.AutelProductType
import com.autel.sdk.Autel
import com.autel.sdk.ProductConnectListener
import com.autel.sdk.product.BaseProduct
import java.util.concurrent.atomic.AtomicBoolean

class DFWayPointActivity : AppCompatActivity() {
    lateinit var viewModel: DFViewModel
    lateinit var binding: ActivityDfwayPointBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDfwayPointBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[DFViewModel::class.java]
        viewModel.setContexts(applicationContext)
        viewModel.initializeData()
        setContentView(binding.root)


        handleListeners()
        deselectAllTabs()
    }

    private fun handleListeners() {

        binding.interfaceDebug.root.setOnClickListener { v ->
            deselectAllTabs()
            binding.interfaceDebug.optionParent.setBackgroundColor(
                ContextCompat.getColor(
                    this@DFWayPointActivity,
                    R.color.blue
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, InterfaceTest_MissionFragment())
                .commitNow()
        }

        binding.aircraftStatus.root.setOnClickListener { v ->
            deselectAllTabs()
            binding.aircraftStatus.optionParent.setBackgroundColor(
                ContextCompat.getColor(
                    this@DFWayPointActivity,
                    R.color.blue
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AirCraftMission_CommandStatusFragment())
                .commitNow()
        }

        binding.flightControl.root.setOnClickListener { v ->
            deselectAllTabs()
            binding.flightControl.optionParent.setBackgroundColor(
                ContextCompat.getColor(
                    this@DFWayPointActivity,
                    R.color.blue
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, ManualIndividual_MissionFragment())
                .commitNow()
        }

        binding.debugLog.root.setOnClickListener { v ->
            deselectAllTabs()
            binding.debugLog.optionParent.setBackgroundColor(
                ContextCompat.getColor(
                    this@DFWayPointActivity,
                    R.color.blue
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AutoTest_MissionFragment())
                .commitNow()
        }

    }


    private fun deselectAllTabs() {
        binding.interfaceDebug.optionParent.setBackgroundColor(
            ContextCompat.getColor(
                this@DFWayPointActivity,
                R.color.white
            )
        )
        binding.aircraftStatus.optionParent.setBackgroundColor(
            ContextCompat.getColor(
                this@DFWayPointActivity,
                R.color.white
            )
        )
        binding.flightControl.optionParent.setBackgroundColor(
            ContextCompat.getColor(
                this@DFWayPointActivity,
                R.color.white
            )
        )
        binding.debugLog.optionParent.setBackgroundColor(
            ContextCompat.getColor(
                this@DFWayPointActivity,
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
                // viewModel.setCurrentProduct(product)
            }

            override fun productDisconnected() {
                Log.v("productType", "productDisconnected ")
                currentType = AutelProductType.UNKNOWN
                //  viewModel.setCurrentProduct(null)
            }
        })
    }
}