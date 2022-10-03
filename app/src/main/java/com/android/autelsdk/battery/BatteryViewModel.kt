package com.android.autelsdk.battery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.RangePair
import com.autel.common.battery.cruiser.CruiserBatteryInfo
import com.autel.common.error.AutelError
import com.autel.common.product.AutelProductType
import com.autel.sdk.battery.AutelBattery
import com.autel.sdk.battery.CruiserBattery
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.product.CruiserAircraft

class BatteryViewModel : ViewModel() {
    private var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(
        AutelProductType.UNKNOWN
    )

    lateinit var product: BaseProduct
    lateinit var battery: CruiserBattery
    lateinit var mController: AutelBattery

    fun setCurrentProduct(product: BaseProduct?) {
        setCurrentProductType(product?.type)
        currentProduct.postValue(product)
        if (product != null) {
            this.product = product
            battery = (product as CruiserAircraft).battery
        }
    }

    private fun setCurrentProductType(productType: AutelProductType?) {
        currentProductType.postValue(productType ?: AutelProductType.UNKNOWN)
    }

    fun getCurrentProductType(): MutableLiveData<AutelProductType> {
        return currentProductType
    }

    fun setController(controller: AutelBattery) {
        mController = controller
    }

    fun getDischargeCount() {
        mController!!.getDischargeDay(object : CallbackWithOneParam<Int> {
            override fun onFailure(error: AutelError) {
                //logOut("getDischargeDay  error :  " + error.description)
            }

            override fun onSuccess(data: Int) {
                //logOut("getDischargeDay  data :  $data")
            }
        })
    }

    fun getVersion() {
        mController!!.getVersion(object : CallbackWithOneParam<String> {
            override fun onSuccess(data: String) {
                //  logOut("getVersion  $data")
            }

            override fun onFailure(error: AutelError) {
                // logOut("getVersion  error : " + error.description)
            }
        })
    }

    fun getSerialNumber() {
        mController!!.getSerialNumber(object : CallbackWithOneParam<String> {
            override fun onSuccess(data: String) {
                // logOut("getSerialNumber  $data")
            }

            override fun onFailure(error: AutelError) {
                // logOut("getSerialNumber  error : " + error.description)
            }
        })
    }

    fun getFullChargeCapacity() {
        mController!!.getFullChargeCapacity(object : CallbackWithOneParam<Int> {
            override fun onSuccess(data: Int) {
                //  logOut("getFullChargeCapacity  $data")
            }

            override fun onFailure(error: AutelError) {
                //  logOut("getFullChargeCapacity error : " + error.description)
            }
        })
    }

    fun getCellVoltageRange() {
        mController!!.parameterSupportManager.getBatteryCellVoltageRange(object :
            CallbackWithOneParam<RangePair<Int>> {
            override fun onSuccess(data: RangePair<Int>) {
                // logOut("getBatteryCellVoltageRange  from " + data.valueFrom + " to " + data.valueTo)
            }

            override fun onFailure(error: AutelError) {
                // logOut("getBatteryCellVoltageRange error : " + error.description)
            }
        })
    }

    fun getBatteryRealTimeDataListener() {

        battery?.setBatteryStateListener(object :
            CallbackWithOneParam<CruiserBatteryInfo> {
            override fun onFailure(error: AutelError) {
                //   logOut("setBatteryStateListener  error :  " + error.description)
            }

            override fun onSuccess(data: CruiserBatteryInfo) {
                //  logOut("setBatteryStateListener  data current battery :  $data")
            }
        })
    }

    fun getLowBatteryNotifyThresholdEdt() {
        mController!!.getLowBatteryNotifyThreshold(object : CallbackWithOneParam<Float> {
            override fun onFailure(error: AutelError) {
                //  logOut("getLowBatteryNotifyThreshold  error :  " + error.description)
            }

            override fun onSuccess(data: Float) {
                //  logOut("getLowBatteryNotifyThreshold  data :  $data")
            }
        })
    }

    fun getCriticalBatteryNotifyThresholdEdt() {
        for (index in 0..2) {
            mController!!.getCriticalBatteryNotifyThreshold(object :
                CallbackWithOneParam<Float> {
                override fun onFailure(error: AutelError) {
                    //   logOut("getCriticalBatteryNotifyThreshold  error :  " + error.description + "  time " + System.currentTimeMillis())
                }

                override fun onSuccess(data: Float) {
                    //  logOut("getCriticalBatteryNotifyThreshold  data :  " + data + "  time " + System.currentTimeMillis())
                }
            })
        }
    }

    fun getDischargeDayEdt() {
        mController!!.getDischargeDay(object : CallbackWithOneParam<Int> {
            override fun onFailure(error: AutelError) {
                //  logOut("getDischargeDay  error :  " + error.description)
            }

            override fun onSuccess(data: Int) {
                //  logOut("getDischargeDay  data :  $data")
            }
        })
    }

    fun setBatteryRealTimeDataListener() {
        battery = (product as CruiserAircraft).battery
        battery?.setBatteryStateListener(object :
            CallbackWithOneParam<CruiserBatteryInfo> {
            override fun onFailure(error: AutelError) {
                //  logOut("setBatteryStateListener  error :  " + error.description)
            }

            override fun onSuccess(data: CruiserBatteryInfo) {
                //  logOut("setBatteryStateListener  data current battery :  $data")
            }
        })
    }

    protected fun isEmpty(value: String?): Boolean {
        return null == value || "" == value
    }

    fun setLowBatteryNotifyThresholdEdt(value: String) {
        mController!!.setLowBatteryNotifyThreshold(
            if (isEmpty(value)) 0.25f else java.lang.Float.valueOf(
                value
            ), object : CallbackWithNoParam {
                override fun onFailure(error: AutelError) {
                    // logOut("setLowBatteryNotifyThreshold  error :  " + error.description)
                }

                override fun onSuccess() {
                    // logOut("setLowBatteryNotifyThreshold   onSuccess ")
                }
            })
    }

    fun setCriticalBatteryNotifyThresholdEdt(value: String) {
        mController!!.setCriticalBatteryNotifyThreshold(
            if (isEmpty(value)) 0.25f else java.lang.Float.valueOf(
                value
            ), object : CallbackWithNoParam {
                override fun onFailure(error: AutelError) {
                    //  logOut("setCriticalBatteryNotifyThreshold  error :  " + error.description)
                }

                override fun onSuccess() {
                    //   logOut("setCriticalBatteryNotifyThreshold  onSuccess  ")
                }
            })
    }

    fun setDischargeDayEdt(value: String) {
        mController!!.setDischargeDay(
            if (isEmpty(value)) 2 else Integer.valueOf(value),
            object : CallbackWithNoParam {
                override fun onSuccess() {
                    //    logOut("setDischargeDay  onSuccess  ")
                }

                override fun onFailure(autelError: AutelError) {
                    //   logOut("setDischargeDay  error :  " + autelError.description)
                }
            })
    }

}