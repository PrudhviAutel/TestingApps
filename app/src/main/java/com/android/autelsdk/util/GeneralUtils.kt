package com.android.autelsdk.util

import com.android.autelsdk.battery.data.ACDataModel

class GeneralUtils {

    companion object {
        fun getBatteryManualIndividualArrayList(): ArrayList<ACDataModel> {
            var list = ArrayList<ACDataModel>()
            list.add(
                ACDataModel(
                    "setBatteryRealTimeDataListener",
                    "setBatteryRealTimeDataListener"
                )
            )
            list.add(
                ACDataModel(
                    "getBatteryRealTimeDataListener",
                    "getBatteryRealTimeDataListener"
                )
            )
            list.add(
                ACDataModel(
                    "setLowBatteryNotifyThresholdEdt",
                    "setLowBatteryNotifyThresholdEdt"
                )
            )
            list.add(
                ACDataModel(
                    "getLowBatteryNotifyThresholdEdt",
                    "getLowBatteryNotifyThresholdEdt"
                )
            )
            list.add(
                ACDataModel(
                    "setCriticalBatteryNotifyThresholdEdt",
                    "setCriticalBatteryNotifyThresholdEdt"
                )
            )
            list.add(
                ACDataModel(
                    "getCriticalBatteryNotifyThresholdEdt",
                    "getCriticalBatteryNotifyThresholdEdt"
                )
            )
            list.add(ACDataModel("setDischargeDayEdt", "setDischargeDayEdt"))
            list.add(ACDataModel("getDischargeDayEdt", "getDischargeDayEdt"))
            return list
        }

        fun getBatteryAirCraftStatusCommandList(): ArrayList<ACDataModel> {
            var list = ArrayList<ACDataModel>()
            val acDataModel = ACDataModel("1", "1")

            list.add(ACDataModel("getDischargeCount", "getDischargeCount"))
            list.add(ACDataModel("getVersion", "getVersion"))
            list.add(ACDataModel("getSerialNumber", "getSerialNumber"))
            list.add(ACDataModel("getFullChargeCapacity", "getFullChargeCapacity"))
            list.add(ACDataModel("getCellVoltageRange", "getCellVoltageRange"))
            return list
        }

        fun getBatteryAutoTestArrayList(): ArrayList<String> {
            var list = ArrayList<String>()
            list.add("setBatteryRealTimeDataListener")
            list.add("getBatteryRealTimeDataListener")
            list.add("setLowBatteryNotifyThresholdEdt")
            list.add("getLowBatteryNotifyThresholdEdt")
            list.add("setCriticalBatteryNotifyThresholdEdt")
            list.add("getCriticalBatteryNotifyThresholdEdt")
            list.add("setDischargeDayEdt")
            list.add("getDischargeDayEdt")
            list.add("getDischargeCount")
            list.add("getVersion")
            list.add("getSerialNumber")
            list.add("getFullChargeCapacity")
            list.add("getCellVoltageRange")
            return list
        }
    }
}