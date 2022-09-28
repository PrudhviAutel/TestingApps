package com.android.autelsdk.base

import com.android.autelsdk.BaseActivity
import com.autel.sdk.battery.AutelBattery
import android.widget.EditText
import android.os.Bundle
import com.autel.sdk.product.BaseProduct
import android.widget.TextView
import android.text.TextWatcher
import com.autel.common.battery.BatteryParameterRangeManager
import com.autel.common.RangePair
import android.text.Editable
import android.view.View
import com.android.autelsdk.R
import com.autel.common.CallbackWithOneParam
import com.autel.common.error.AutelError
import com.autel.common.CallbackWithNoParam

open class BatteryActivity : BaseActivity<AutelBattery?>() {
    private var lowBatteryNotifyThreshold: EditText? = null
    private var criticalBatteryNotifyThreshold: EditText? = null
    private var dischargeDay: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Battery"
    }

    override fun initController(product: BaseProduct): AutelBattery {
        return product.battery
    }

    override fun getCustomViewResId(): Int {
        return R.layout.ac_base_battery
    }

    override fun initUi() {
        lowBatteryNotifyThreshold = findViewById<View>(R.id.lowBatteryNotifyThreshold) as EditText
        criticalBatteryNotifyThreshold =
            findViewById<View>(R.id.criticalBatteryNotifyThreshold) as EditText
        dischargeDay = findViewById<View>(R.id.dischargeDay) as EditText
        val lowBatteryRange = findViewById<View>(R.id.lowBatteryRange) as TextView
        lowBatteryNotifyThreshold!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (isEmpty(lowBatteryRange.text.toString())) {
                    val batteryParameterRangeManager = mController!!.parameterSupportManager
                    val support = batteryParameterRangeManager.lowBattery
                    lowBatteryRange.text =
                        "low battery range from " + support.valueFrom + " to " + support.valueTo
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        val criticalBatteryRange = findViewById<View>(R.id.criticalBatteryRange) as TextView
        criticalBatteryNotifyThreshold!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (isEmpty(criticalBatteryRange.text.toString())) {
                    val batteryParameterRangeManager = mController!!.parameterSupportManager
                    val support = batteryParameterRangeManager.criticalBattery
                    criticalBatteryRange.text =
                        "critical battery range from " + support.valueFrom + " to " + support.valueTo
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        val dischargeDayRange = findViewById<View>(R.id.dischargeDayRange) as TextView
        dischargeDay!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (isEmpty(dischargeDayRange.text.toString())) {
                    val batteryParameterRangeManager = mController!!.parameterSupportManager
                    val support = batteryParameterRangeManager.dischargeDay
                    dischargeDayRange.text =
                        "discharge day range from " + support.valueFrom + " to " + support.valueTo
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        initClick()
    }

    private fun initClick() {
        findViewById<View>(R.id.getLowBatteryNotifyThreshold).setOnClickListener {
            mController!!.getLowBatteryNotifyThreshold(object : CallbackWithOneParam<Float> {
                override fun onFailure(error: AutelError) {
                    logOut("getLowBatteryNotifyThreshold  error :  " + error.description)
                }

                override fun onSuccess(data: Float) {
                    logOut("getLowBatteryNotifyThreshold  data :  $data")
                }
            })
        }
        findViewById<View>(R.id.setLowBatteryNotifyThreshold).setOnClickListener {
            val value = lowBatteryNotifyThreshold!!.text.toString()
            mController!!.setLowBatteryNotifyThreshold(
                if (isEmpty(value)) 0.25f else java.lang.Float.valueOf(
                    value
                ), object : CallbackWithNoParam {
                    override fun onFailure(error: AutelError) {
                        logOut("setLowBatteryNotifyThreshold  error :  " + error.description)
                    }

                    override fun onSuccess() {
                        logOut("setLowBatteryNotifyThreshold   onSuccess ")
                    }
                })
        }
        findViewById<View>(R.id.getCriticalBatteryNotifyThreshold).setOnClickListener {
            for (index in 0..2) {
                mController!!.getCriticalBatteryNotifyThreshold(object :
                    CallbackWithOneParam<Float> {
                    override fun onFailure(error: AutelError) {
                        logOut("getCriticalBatteryNotifyThreshold  error :  " + error.description + "  time " + System.currentTimeMillis())
                    }

                    override fun onSuccess(data: Float) {
                        logOut("getCriticalBatteryNotifyThreshold  data :  " + data + "  time " + System.currentTimeMillis())
                    }
                })
            }
        }
        findViewById<View>(R.id.setCriticalBatteryNotifyThreshold).setOnClickListener {
            val value = criticalBatteryNotifyThreshold!!.text.toString()
            mController!!.setCriticalBatteryNotifyThreshold(
                if (isEmpty(value)) 0.25f else java.lang.Float.valueOf(
                    value
                ), object : CallbackWithNoParam {
                    override fun onFailure(error: AutelError) {
                        logOut("setCriticalBatteryNotifyThreshold  error :  " + error.description)
                    }

                    override fun onSuccess() {
                        logOut("setCriticalBatteryNotifyThreshold  onSuccess  ")
                    }
                })
        }
        findViewById<View>(R.id.getDischargeDay).setOnClickListener {
            mController!!.getDischargeDay(object : CallbackWithOneParam<Int> {
                override fun onFailure(error: AutelError) {
                    logOut("getDischargeDay  error :  " + error.description)
                }

                override fun onSuccess(data: Int) {
                    logOut("getDischargeDay  data :  $data")
                }
            })
        }
        findViewById<View>(R.id.setDischargeDay).setOnClickListener {
            val value = dischargeDay!!.text.toString()
            mController!!.setDischargeDay(
                if (isEmpty(value)) 2 else Integer.valueOf(value),
                object : CallbackWithNoParam {
                    override fun onSuccess() {
                        logOut("setDischargeDay  onSuccess  ")
                    }

                    override fun onFailure(autelError: AutelError) {
                        logOut("setDischargeDay  error :  " + autelError.description)
                    }
                })
        }
        findViewById<View>(R.id.getDischargeCount).setOnClickListener {
            mController!!.getDischargeCount(object : CallbackWithOneParam<Int> {
                override fun onSuccess(data: Int) {
                    logOut("getDischargeCount  $data")
                }

                override fun onFailure(error: AutelError) {
                    logOut("getDischargeCount error : " + error.description)
                }
            })
        }
        findViewById<View>(R.id.getVersion).setOnClickListener {
            mController!!.getVersion(object : CallbackWithOneParam<String> {
                override fun onSuccess(data: String) {
                    logOut("getVersion  $data")
                }

                override fun onFailure(error: AutelError) {
                    logOut("getVersion  error : " + error.description)
                }
            })
        }
        findViewById<View>(R.id.getSerialNumber).setOnClickListener {
            mController!!.getSerialNumber(object : CallbackWithOneParam<String> {
                override fun onSuccess(data: String) {
                    logOut("getSerialNumber  $data")
                }

                override fun onFailure(error: AutelError) {
                    logOut("getSerialNumber  error : " + error.description)
                }
            })
        }
        findViewById<View>(R.id.getFullChargeCapacity).setOnClickListener {
            mController!!.getFullChargeCapacity(object : CallbackWithOneParam<Int> {
                override fun onSuccess(data: Int) {
                    logOut("getFullChargeCapacity  $data")
                }

                override fun onFailure(error: AutelError) {
                    logOut("getFullChargeCapacity error : " + error.description)
                }
            })
        }
        findViewById<View>(R.id.getCellVoltageRange).setOnClickListener {
            mController!!.parameterSupportManager.getBatteryCellVoltageRange(object :
                CallbackWithOneParam<RangePair<Int>> {
                override fun onSuccess(data: RangePair<Int>) {
                    logOut("getBatteryCellVoltageRange  from " + data.valueFrom + " to " + data.valueTo)
                }

                override fun onFailure(error: AutelError) {
                    logOut("getBatteryCellVoltageRange error : " + error.description)
                }
            })
        }
    }
}