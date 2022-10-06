package com.android.autelsdk.battery

import android.os.Bundle
import android.view.View
import com.android.autelsdk.R
import com.android.autelsdk.base.BatteryActivity
import com.autel.common.CallbackWithOneParam
import com.autel.common.battery.cruiser.CruiserBatteryInfo
import com.autel.common.error.AutelError
import com.autel.sdk.battery.AutelBattery
import com.autel.sdk.battery.CruiserBattery
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.product.CruiserAircraft


class BatteryActivity : BatteryActivity() {

    var battery: CruiserBattery? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = resources.getString(R.string.battery)
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_battery
    }

    override fun initController(product: BaseProduct): AutelBattery {
        battery = (product as CruiserAircraft).battery
        return super.initController(product)
    }

    override fun initUi() {
        super.initUi()
        findViewById<View>(R.id.setBatteryRealTimeDataListener).setOnClickListener {
            battery?.setBatteryStateListener(object :
                CallbackWithOneParam<CruiserBatteryInfo> {
                override fun onFailure(error: AutelError) {
                    logOut("setBatteryStateListener  error :  " + error.description)
                }

                override fun onSuccess(data: CruiserBatteryInfo) {
                    logOut("setBatteryStateListener  data current battery :  $data")
                }
            })
        }
    }
}