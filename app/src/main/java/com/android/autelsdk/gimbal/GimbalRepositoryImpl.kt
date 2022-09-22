package com.android.autelsdk.gimbal

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.android.autelsdk.util.Utils
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.RangePair
import com.autel.common.error.AutelError
import com.autel.common.gimbal.*
import com.autel.common.gimbal.evo.EvoAngleInfo
import com.autel.common.gimbal.evo.EvoGimbalParameterRangeManager
import com.autel.common.gimbal.evo.GimbalAngleRange
import com.autel.common.remotecontroller.RemoteControllerParameterUnit
import com.autel.internal.gimbal.cruiser.CruiserGimbalImpl
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.gimbal.CruiserGimbal

class GimbalRepositoryImpl() : GimbalRepository {

    var autelGimbalController: AutelGimbal = CruiserGimbalImpl()
    var cruisalGimbalController : CruiserGimbal = CruiserGimbalImpl()

    override fun <T> setController(controller: T) {
        if (controller is AutelGimbal) {
            autelGimbalController = controller
        } else if (controller is CruiserGimbal) {
            cruisalGimbalController = controller
        }
    }

    //AutelGimbal Controller Tests
    override suspend fun setGimbalWorkModeTest(mode: GimbalWorkMode): MutableLiveData<Resource<String>> {
        var setGimbalWorkModeTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        autelGimbalController.setGimbalWorkMode(mode, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                setGimbalWorkModeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\non Mode = ${mode.name} "
                );
                setGimbalWorkModeTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setGimbalWorkModeTestResult
    }

    override suspend fun getGimbalWorkModeTest(): MutableLiveData<Resource<GimbalWorkMode>> {
        var getGimbalWorkModeTestResult: MutableLiveData<Resource<GimbalWorkMode>> = MutableLiveData()
        autelGimbalController.getGimbalWorkMode(object : CallbackWithOneParam<GimbalWorkMode> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getGimbalWorkModeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(mode: GimbalWorkMode) {
                val successMessage = Utils.getSuccessShowText(
                    "\nMode = ${mode.name} "
                );
                getGimbalWorkModeTestResult.postValue(Resource.Companion.success(mode, successMessage))
            }
        })
        return getGimbalWorkModeTestResult
    }

    override suspend fun getVersionInfoTest(): MutableLiveData<Resource<GimbalVersionInfo>> {
        var getVersionInfoTestResult: MutableLiveData<Resource<GimbalVersionInfo>> = MutableLiveData()
        autelGimbalController.getVersionInfo(object : CallbackWithOneParam<GimbalVersionInfo> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                methodName = "getVersionInfo");
                getVersionInfoTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(info: GimbalVersionInfo) {
                val successMessage = Utils.getSuccessShowText(
                    "\nMode = ${info} "
                );
                getVersionInfoTestResult.postValue(Resource.Companion.success(info, successMessage))
            }
        })
        return getVersionInfoTestResult
    }

    override fun getGimbalParameterRangeManager(): GimbalParameterRangeManager {
        return autelGimbalController.parameterRangeManager
    }

    //CruiserGimbal Controller Tests
    override suspend fun setAngleListenerTest(): MutableLiveData<Resource<EvoAngleInfo>> {
        var setAngleListenerTestResult: MutableLiveData<Resource<EvoAngleInfo>> = MutableLiveData()
        cruisalGimbalController.setAngleListener(object : CallbackWithOneParam<EvoAngleInfo> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                setAngleListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(evoAngleInfo: EvoAngleInfo?) {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw Unit = ${evoAngleInfo?.yaw} "
                );
                setAngleListenerTestResult.postValue(Resource.Companion.success(evoAngleInfo, successMessage))
            }
        })
        return setAngleListenerTestResult
    }

    override suspend fun getAngleRangeTest(): MutableLiveData<Resource<GimbalAngleRange>> {
        var getAngleListenerTestResult: MutableLiveData<Resource<GimbalAngleRange>> = MutableLiveData()
        cruisalGimbalController.parameterRangeManager.getAngleRange(object : CallbackWithOneParam<GimbalAngleRange> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getAngleListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: GimbalAngleRange?) {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw Unit = ${data?.yawMin} "
                );
                getAngleListenerTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getAngleListenerTestResult
    }

    override suspend fun getAngleSpeedRangeTest(): MutableLiveData<Resource<RangePair<Int>>> {
        var getAngleListenerTestResult: MutableLiveData<Resource<RangePair<Int>>> = MutableLiveData()
        val support = cruisalGimbalController.parameterRangeManager.angleSpeedRange
        val successMessage = "Gimbal Angle Speed Range is from ${support.valueFrom} to ${support.valueTo}"
        getAngleListenerTestResult.postValue(Resource.Companion.success(support, successMessage));
        return getAngleListenerTestResult
    }

    override suspend fun setGimbalAngleTest(pitch: Float, roll: Float, yaw: Float) {

        cruisalGimbalController.setGimbalAngle(pitch, roll, yaw)

    }

    override suspend fun setGimbalAngleSpeedTest(pitch: Int, yaw: Int) {
        cruisalGimbalController.setGimbalAngleWithSpeed(pitch, yaw)
    }

    override suspend fun resetGimbalAngleTest(gimbalAxisType: GimbalAxisType): MutableLiveData<Resource<String>> {
        var resetGimbalAngleTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruisalGimbalController.resetGimbalAngle(gimbalAxisType,object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                resetGimbalAngleTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\nGimbal Axis Type reset to ${gimbalAxisType.name} "
                );
                resetGimbalAngleTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return resetGimbalAngleTestResult
    }

    override suspend fun setRollAdjustDataTest(roll: Float) {
        cruisalGimbalController.setRollAdjustData(roll)
    }

    override suspend fun getRollAdjustDataTest() : MutableLiveData<Resource<Double>> {
        var getRollAdjustDataTestResult: MutableLiveData<Resource<Double>> = MutableLiveData()
        cruisalGimbalController.getRollAdjustData(object : CallbackWithOneParam<Double> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getRollAdjustDataTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: Double) {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw Unit = ${data} "
                );
                getRollAdjustDataTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getRollAdjustDataTestResult
    }

    override suspend fun setLeaserRadarListenerTest(): MutableLiveData<Resource<LeaserRadar>> {
        var setLeaserRadarListenerTestResult: MutableLiveData<Resource<LeaserRadar>> = MutableLiveData()
        cruisalGimbalController.setLeaserRadarListener(object : CallbackWithOneParam<LeaserRadar> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                setLeaserRadarListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: LeaserRadar) {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw Unit = ${data} "
                );
                setLeaserRadarListenerTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return setLeaserRadarListenerTestResult
    }

    override fun resetLeaserRadarListenerTest() {
        cruisalGimbalController.setLeaserRadarListener(null)
    }

    override suspend fun adjustGimbalDirectionTest(x: Float, y: Float, pitch: Float, roll: Float, yaw: Float): MutableLiveData<Resource<String>> {
        var adjustGimbalDirectionTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruisalGimbalController.adjustGimbalDirection(x, y, pitch, yaw, roll)
        val successMessage = "Adjusted values x = ${x}, y = ${y}, pitch = ${pitch}, yaw = ${yaw}, roll = ${roll} successfully.\nMake sure your drone is connected, otherwise values will not be set."
        adjustGimbalDirectionTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return adjustGimbalDirectionTestResult
    }

    override fun getCruiserGimbalParameterRangeManager(): EvoGimbalParameterRangeManager {
        return cruisalGimbalController.parameterRangeManager
    }

    override suspend fun setYawAdjustDataTest(yaw: Float): MutableLiveData<Resource<String>> {
        var setYawAdjustDataTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruisalGimbalController.setYawAdjustData(yaw)
        setYawAdjustDataTestResult.postValue(Resource.Companion.success("Adjusted yaw = ${yaw}"))
        return setYawAdjustDataTestResult
    }

    override suspend fun getYawAdjustDataTest(): MutableLiveData<Resource<Double>> {
        var getYawAdjustDataTestResult: MutableLiveData<Resource<Double>> = MutableLiveData()
        cruisalGimbalController.getYawAdjustData(object : CallbackWithOneParam<Double> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getYawAdjustDataTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: Double) {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw  = ${data} "
                );
                getYawAdjustDataTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getYawAdjustDataTestResult
    }

    override suspend fun setPitchAdjustDataTest(pitch: Float): MutableLiveData<Resource<String>> {
        var setPitchAdjustDataTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruisalGimbalController.setPitchAdjustData(pitch)
        setPitchAdjustDataTestResult.postValue(Resource.Companion.success("Adjusted pitch = ${pitch}"))
        return setPitchAdjustDataTestResult
    }

    override suspend fun getPitchAdjustDataTest(): MutableLiveData<Resource<Double>> {
        var getYawAdjustDataTestResult: MutableLiveData<Resource<Double>> = MutableLiveData()
        cruisalGimbalController.getYawAdjustData(object : CallbackWithOneParam<Double> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getYawAdjustDataTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: Double) {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw  = ${data} "
                );
                getYawAdjustDataTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getYawAdjustDataTestResult
    }

    override suspend fun setSaveParamsTest(): MutableLiveData<Resource<String>> {
        var setGimbalCalibrationTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruisalGimbalController.setSaveParams(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                setGimbalCalibrationTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw  =  "
                );
                setGimbalCalibrationTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setGimbalCalibrationTestResult
    }

    override suspend fun setGimbalCalibrationTest(): MutableLiveData<Resource<String>> {
        var setGimbalCalibrationTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruisalGimbalController.setGimbalCalibration(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                setGimbalCalibrationTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw  =  "
                );
                setGimbalCalibrationTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setGimbalCalibrationTestResult
    }

    override suspend fun stopGimbalCalibrationTest(): MutableLiveData<Resource<String>> {
        var stopGimbalCalibrationTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruisalGimbalController.stopGimbalCalibration(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                stopGimbalCalibrationTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw  = "
                );
                stopGimbalCalibrationTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return stopGimbalCalibrationTestResult
    }

    override suspend fun getAdjustGimbalAngelDataTest(): MutableLiveData<Resource<GimbalAdjustmentAngle>> {
        var getAdjustGimbalAngelDataTestResult: MutableLiveData<Resource<GimbalAdjustmentAngle>> = MutableLiveData()
        cruisalGimbalController.getAdjustGimbalAngelData(object : CallbackWithOneParam<GimbalAdjustmentAngle> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getAdjustGimbalAngelDataTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: GimbalAdjustmentAngle) {
                val successMessage = Utils.getSuccessShowText(
                    "\nYaw  = ${data} "
                );
                getAdjustGimbalAngelDataTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getAdjustGimbalAngelDataTestResult
    }


}
