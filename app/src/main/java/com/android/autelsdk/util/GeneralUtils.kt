package com.android.autelsdk.util

class GeneralUtils {

    companion object {
        fun getBatteryManualIndividualArrayList(): ArrayList<String> {
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

        fun getTypeOfIndividualTest() {

        }
    }
}