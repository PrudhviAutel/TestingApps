package com.android.autelsdk.battery

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.battery.data.HarnessResult
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.RangePair
import com.autel.common.battery.cruiser.CruiserBatteryInfo
import com.autel.common.error.AutelError
import com.autel.common.product.AutelProductType
import com.autel.internal.battery.cruiser.CruiserBatteryImpl
import com.autel.sdk.battery.AutelBattery
import com.autel.sdk.battery.CruiserBattery
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.product.CruiserAircraft

class BatteryViewModel : ViewModel() {
    var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    var result: MutableLiveData<HarnessResult> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(
        AutelProductType.UNKNOWN
    )

    var product: BaseProduct? = null
    var battery: CruiserBattery = CruiserBatteryImpl()
    var mController: AutelBattery = CruiserBatteryImpl()

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

    fun getDischargeCount(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.getDischargeDay(object : CallbackWithOneParam<Int> {
            override fun onFailure(error: AutelError) {
                //logOut("getDischargeDay  error :  " + error.description)
                result.postValue(
                    HarnessResult(
                        "getDischargeDay()  error :  " + error.description,
                        false
                    )
                )
            }

            override fun onSuccess(data: Int) {
                //logOut("getDischargeDay  data :  $data")
                result.postValue(HarnessResult("getDischargeDay()  $data", true))
            }
        })
        return result
    }

    fun getVersion(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.getVersion(object : CallbackWithOneParam<String> {
            override fun onSuccess(data: String) {
                //  logOut("getVersion  $data")
                result.postValue(HarnessResult("getVersion()  $data", true))
            }

            override fun onFailure(error: AutelError) {
                // logOut("getVersion  error : " + error.description)
                result.postValue(HarnessResult("getVersion()  error : " + error.description, false))
            }
        })
        return result
    }

    fun getSerialNumber(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.getSerialNumber(object : CallbackWithOneParam<String> {
            override fun onSuccess(data: String) {
                // logOut("getSerialNumber  $data")
                result.postValue(HarnessResult("getSerialNumber()  $data", true))
                Log.d("Rahul ", "getSerialNumber()  $data")
            }

            override fun onFailure(error: AutelError) {
                // logOut("getSerialNumber  error : " + error.description)
                result.postValue(
                    HarnessResult(
                        "getSerialNumber()  error : " + error.description,
                        false
                    )
                )
                Log.d("Rahul ", "getSerialNumber()  error : " + error.description)
            }
        })
        return result
    }

    fun getFullChargeCapacity(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.getFullChargeCapacity(object : CallbackWithOneParam<Int> {
            override fun onSuccess(data: Int) {
                //  logOut("getFullChargeCapacity  $data")
                result.postValue(HarnessResult("getFullChargeCapacity()  $data", true))
            }

            override fun onFailure(error: AutelError) {
                //  logOut("getFullChargeCapacity error : " + error.description)
                result.postValue(
                    HarnessResult(
                        "getFullChargeCapacity() error : " + error.description,
                        false
                    )
                )
            }
        })
        return result
    }

    fun getCellVoltageRange(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.parameterSupportManager.getBatteryCellVoltageRange(object :
            CallbackWithOneParam<RangePair<Int>> {
            override fun onSuccess(data: RangePair<Int>) {
                // logOut("getBatteryCellVoltageRange  from " + data.valueFrom + " to " + data.valueTo)
                result.postValue(
                    HarnessResult(
                        "getBatteryCellVoltageRange()  from " + data.valueFrom + " to " + data.valueTo,
                        true
                    )
                )
            }

            override fun onFailure(error: AutelError) {
                // logOut("getBatteryCellVoltageRange error : " + error.description)
                result.postValue(
                    HarnessResult(
                        "getBatteryCellVoltageRange() error : " + error.description,
                        false
                    )
                )
            }
        })
        return result
    }

    fun resetBatteryRealTimeDataListener(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        battery.setBatteryStateListener(null)
        result.postValue(
            HarnessResult(
                "setBatteryStateListener()  Reset :  Successful",
                true
            )
        )
        return result
    }

    fun getLowBatteryNotifyThresholdEdt(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.getLowBatteryNotifyThreshold(object : CallbackWithOneParam<Float> {
            override fun onFailure(error: AutelError) {
                //  logOut("getLowBatteryNotifyThreshold  error :  " + error.description)
                Log.e("Error ", "" + error.description)
                result.postValue(
                    HarnessResult(
                        "getLowBatteryNotifyThreshold()  error :  " + error.description,
                        false
                    )
                )
            }

            override fun onSuccess(data: Float) {
                //  logOut("getLowBatteryNotifyThreshold  data :  $data")
                Log.e("Success ", "$data")
                result.postValue(
                    HarnessResult(
                        "getLowBatteryNotifyThreshold()  data :   + $data",
                        true
                    )
                )
            }
        })
        return result
    }

    fun getCriticalBatteryNotifyThresholdEdt(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        for (index in 0..2) {
            mController!!.getCriticalBatteryNotifyThreshold(object :
                CallbackWithOneParam<Float> {
                override fun onFailure(error: AutelError) {
                    //   logOut("getCriticalBatteryNotifyThreshold  error :  " + error.description + "  time " + System.currentTimeMillis())
                    result.postValue(
                        HarnessResult(
                            "getCriticalBatteryNotifyThreshold()  error :  " + error.description + "  time " + System.currentTimeMillis(),
                            false
                        )
                    )
                }

                override fun onSuccess(data: Float) {
                    //  logOut("getCriticalBatteryNotifyThreshold  data :  " + data + "  time " + System.currentTimeMillis())
                    result.postValue(
                        HarnessResult(
                            "getCriticalBatteryNotifyThreshold()  data :  " + data + "  time " + System.currentTimeMillis(),
                            true
                        )
                    )
                }
            })
        }
        return result
    }

    fun getDischargeDayEdt(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.getDischargeDay(object : CallbackWithOneParam<Int> {
            override fun onFailure(error: AutelError) {
                //  logOut("getDischargeDay  error :  " + error.description)
                result.postValue(
                    HarnessResult("getDischargeDay()  error :  " + error.description, false)
                )
                Log.d("Rahul ", "getDischargeDay()  error :  " + error.description)
            }

            override fun onSuccess(data: Int) {
                //  logOut("getDischargeDay  data :  $data")
                result.postValue(
                    HarnessResult("getDischargeDay()  data :  $data", true)
                )
                Log.d("Rahul ", "getDischargeDay()  data :  $data")
            }
        })
        return result
    }

    fun setBatteryRealTimeDataListener(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        //  if (product != null) {
        battery.setBatteryStateListener(object :
            CallbackWithOneParam<CruiserBatteryInfo> {
            override fun onFailure(error: AutelError) {
                //  logOut("setBatteryStateListener  error :  " + error.description)
                result.postValue(
                    HarnessResult(
                        "setBatteryStateListener()  error :  " + error.description,
                        false
                    )
                )
            }

            override fun onSuccess(data: CruiserBatteryInfo) {
                //  logOut("setBatteryStateListener  data current battery :  $data")
                result.postValue(
                    HarnessResult(
                        "setBatteryStateListener()  data current battery :  $data", true
                    )
                )

            }
        })
        /*  } else {
              result.postValue(
                  HarnessResult(
                      "setBatteryStateListener() error :  product is not connected", false
                  )
              )
          }*/
        return result
    }

    protected fun isEmpty(value: String?): Boolean {
        return null == value || "" == value
    }

    fun setLowBatteryNotifyThresholdEdt(value: String): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.setLowBatteryNotifyThreshold(
            if (isEmpty(value)) 0.25f else java.lang.Float.valueOf(
                value
            ), object : CallbackWithNoParam {
                override fun onFailure(error: AutelError) {
                    result.postValue(
                        HarnessResult(
                            "setLowBatteryNotifyThreshold()  error :  " + error.description,
                            false
                        )
                    )
                    // logOut("setLowBatteryNotifyThreshold  error :  " + error.description)
                }

                override fun onSuccess() {
                    // logOut("setLowBatteryNotifyThreshold   onSuccess ")
                    result.postValue(
                        HarnessResult(
                            "setLowBatteryNotifyThreshold()   onSuccess ",
                            true
                        )
                    )
                }
            })
        return result
    }

    fun setCriticalBatteryNotifyThresholdEdt(value: String): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.setCriticalBatteryNotifyThreshold(
            if (isEmpty(value)) 0.25f else java.lang.Float.valueOf(
                value
            ), object : CallbackWithNoParam {
                override fun onFailure(error: AutelError) {
                    //  logOut("setCriticalBatteryNotifyThreshold  error :  " + error.description)
                    result.postValue(
                        HarnessResult(
                            "setCriticalBatteryNotifyThreshold()  error :  " + error.description,
                            false
                        )
                    )
                }

                override fun onSuccess() {
                    //   logOut("setCriticalBatteryNotifyThreshold  onSuccess  ")
                    result.postValue(
                        HarnessResult(
                            "setCriticalBatteryNotifyThreshold()  onSuccess",
                            true
                        )
                    )
                }
            })
        return result
    }

    fun setDischargeDayEdt(value: String): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.setDischargeDay(
            if (isEmpty(value)) 2 else Integer.valueOf(value),
            object : CallbackWithNoParam {
                override fun onSuccess() {
                    //    logOut("setDischargeDay  onSuccess  ")
                    result.postValue(HarnessResult("setDischargeDay() onSuccess", true))
                }

                override fun onFailure(autelError: AutelError) {
                    //   logOut("setDischargeDay  error :  " + autelError.description)
                    result.postValue(
                        HarnessResult(
                            "setDischargeDay()  error :  " + autelError.description,
                            false
                        )
                    )
                }
            })
        return result
    }

    fun getSerialNumberTest(): MutableLiveData<HarnessResult?> {
        var result: MutableLiveData<HarnessResult?> = MutableLiveData()
        mController!!.getSerialNumber(object : CallbackWithOneParam<String> {
            override fun onSuccess(data: String) {
                //logOut("getSerialNumber  $data")
                result.postValue(HarnessResult("getSerialNumber()  $data", true))
            }

            override fun onFailure(error: AutelError) {
                //logOut("getSerialNumber  error : " + error.description)
                result.postValue(
                    HarnessResult(
                        "getSerialNumber()  error : \n" + error.description,
                        false
                    )
                )
            }
        })
        return result
    }

}