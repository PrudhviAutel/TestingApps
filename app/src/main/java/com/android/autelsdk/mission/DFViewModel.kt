package com.android.autelsdk.mission

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.autelsdk.TestApplication
import com.android.autelsdk.rxrunnable.IOUiRunnable
import com.android.autelsdk.util.FileUtils
import com.autel.common.CallbackWithOneParam
import com.autel.common.CallbackWithOneParamProgress
import com.autel.common.battery.cruiser.CruiserBatteryInfo
import com.autel.common.error.AutelError
import com.autel.common.flycontroller.AutoSafeState
import com.autel.common.flycontroller.ModelType
import com.autel.common.flycontroller.SafeCheck
import com.autel.common.flycontroller.cruiser.CruiserFlyControllerInfo
import com.autel.common.mission.MissionType
import com.autel.common.mission.RealTimeInfo
import com.autel.common.mission.cruiser.CruiserWaypointFinishedAction
import com.autel.common.mission.cruiser.CruiserWaypointMission
import com.autel.common.product.AutelProductType
import com.autel.common.remotecontroller.RemoteControllerInfo
import com.autel.internal.sdk.mission.cruiser.CruiserWaypointRealTimeInfoImpl
import com.autel.sdk.battery.CruiserBattery
import com.autel.sdk.flycontroller.CruiserFlyController
import com.autel.sdk.mission.MissionManager
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController
import com.autel.sdk10.utils.BytesUtils
import com.autel.util.log.AutelLog
import io.reactivex.Observable
import java.util.*

class DFViewModel(val context: Context) : ViewModel() {

    var flyState = FlyState.None
    val filePath = FileUtils.getMissionFilePath().toString() + "mission.aut"
    var autelMission: CruiserWaypointMission? = null
    var mEvoFlyController: CruiserFlyController? = null
    var battery: CruiserBattery? = null
    var remoteController: AutelRemoteController? = null
    var missionManager: MissionManager? = null
    var isDroneCheckFinish = false
    var isDroneOk = false
    var TAG = "DFWayPointActivity"

    enum class FlyState {
        Prepare, Start, Pause, None
    }

    init {
        val product: BaseProduct = (context as TestApplication).getCurrentProduct()
        if (null != product && product.type == AutelProductType.DRAGONFISH) {
            missionManager = product.missionManager
            missionManager?.setRealTimeInfoListener(object : CallbackWithOneParam<RealTimeInfo> {
                override fun onSuccess(realTimeInfo: RealTimeInfo) {
                    val info = realTimeInfo as CruiserWaypointRealTimeInfoImpl
                    AutelLog.d(
                        "MissionRunning",
                        "timeStamp:" + info.timeStamp + ",speed:" + info.speed + ",isArrived:" + info.isArrived +
                                ",isDirecting:" + info.isDirecting + ",waypointSequence:" + info.waypointSequence + ",actionSequence:" + info.actionSequence +
                                ",photoCount:" + info.photoCount + ",MissionExecuteState:" + info.executeState + ",missionID:" + info.missionID
                    )
                }

                override fun onFailure(autelError: AutelError) {}
            })
        }

        battery = product.battery as CruiserBattery
        battery?.getLowBatteryNotifyThreshold(object : CallbackWithOneParam<Float?> {
            override fun onSuccess(aFloat: Float?) {
                //  lowBatteryPercent = aFloat;
            }

            override fun onFailure(autelError: AutelError) {}
        })
        battery?.setBatteryStateListener(object : CallbackWithOneParam<CruiserBatteryInfo> {
            override fun onSuccess(batteryState: CruiserBatteryInfo) {
                AutelLog.d(" batteryState " + batteryState.remainingPercent)
                //  isBatteryOk = batteryState.getRemainingPercent() > lowBatteryPercent;
            }

            override fun onFailure(autelError: AutelError) {}
        })

        mEvoFlyController = product.flyController as CruiserFlyController
        mEvoFlyController?.setFlyControllerInfoListener(object :
            CallbackWithOneParam<CruiserFlyControllerInfo?> {
            override fun onSuccess(evoFlyControllerInfo: CruiserFlyControllerInfo?) {
                if (null != evoFlyControllerInfo && null != evoFlyControllerInfo.flyControllerStatus) {
                    val safeCheck = evoFlyControllerInfo.flyControllerStatus.safeCheck
                    //飞行器自检完成
                    if (safeCheck == SafeCheck.COMPLETE) {
                        if (!isDroneCheckFinish) {
                            isDroneCheckFinish = true
                            getAutoCheckResult(ModelType.ALL)
                        }
                    }
                }
            }

            override fun onFailure(autelError: AutelError) {}
        })

        remoteController = product.remoteController
        remoteController?.setInfoDataListener(object : CallbackWithOneParam<RemoteControllerInfo?> {
            override fun onSuccess(remoteControllerInfo: RemoteControllerInfo?) {
                //isImageTransOk = remoteControllerInfo.getDSPPercentage() >= 30;
            }

            override fun onFailure(autelError: AutelError) {}
        })
        AutelLog.d("init missionManager$missionManager")
        initData()
    }

    private fun initData() {
        autelMission = CruiserWaypointMission()
        autelMission!!.missionId =
            BytesUtils.getInt(UUID.randomUUID().toString().replace("-", "").toByteArray()) //任务id
        autelMission!!.missionType =
            MissionType.Waypoint //任务类型(Waypoint(航点)、RECTANGLE(矩形)、POLYGON(多边形))
        autelMission!!.finishedAction = CruiserWaypointFinishedAction.RETURN_HOME
    }

    fun getAutoCheckResult(modelType: ModelType) {
        if (null != mEvoFlyController) {
            object : IOUiRunnable<AutoSafeState?>() {
                var retryCount = 0

                override fun generateObservable(): Observable<AutoSafeState?>? {
                    return mEvoFlyController!!.toRx().getAutoSafeCheck(modelType)
                }

                override
                fun onNext(safeState: AutoSafeState) {
                    super.onNext(safeState)
                    isDroneOk = (safeState.isLeftSteerNormal
                            && safeState.isRightSteerNormal
                            && safeState.isBehindSteerNormal
                            && safeState.isAirSpeedNormal
                            && safeState.isImu1Normal
                            && safeState.isImu2Normal
                            && (safeState.isGPSNormal || safeState.isRTKNormal)
                            && safeState.isMagnetometer1normal
                            && safeState.isMagnetometer2normal
                            && safeState.isUltrasonicNormal
                            && safeState.isBarometerNormal
                            && safeState.isBatteryNormal
                            && safeState.isGimbalNormal
                            && safeState.isRemoteControllerNormal) //900M连接异常

                    AutelLog.debug_i(
                        TAG,
                        "showAutoCheckResult isBehindSteerNormal:" + safeState.isBehindSteerNormal
                                + " isLeftSteerNormal " + safeState.isLeftSteerNormal
                                + " isRightSteerNormal " + safeState.isRightSteerNormal
                                + " isFontMotorNormal " + safeState.isFontMotorNormal
                                + " isLeftMotorNormal " + safeState.isLeftMotorNormal
                                + " isRightMotorNormal " + safeState.isRightMotorNormal
                                + " isAirSpeedNormal " + safeState.isAirSpeedNormal
                                + " isBarometerNormal " + safeState.isBarometerNormal
                                + " isBatteryNormal " + safeState.isBatteryNormal
                                + " isGimbalNormal " + safeState.isGimbalNormal
                                + " isGPSNormal " + safeState.isGPSNormal
                                + " isRTKNormal " + safeState.isRTKNormal
                                + " isMagnetometer1normal " + safeState.isMagnetometer1normal
                                + " isMagnetometer2normal " + safeState.isMagnetometer2normal
                                + " isImu1Normal " + safeState.isImu1Normal
                                + " isImu2Normal " + safeState.isImu2Normal
                                + " isUltrasonicNormal " + safeState.isUltrasonicNormal
                                + " isGPSNormal " + safeState.isGPSNormal
                                + " isRTKNormal " + safeState.isRTKNormal //                + " isPayloadNormal " + safeState.isPayloadNormal()
                                + " isRemoteControllerNormal " + safeState.isRemoteControllerNormal //                            + " flightMode " + mRequestManager.getApplicationState().getFlightType()//需要先将遥控器档位拨至A档
                                + " isDroneOk " + isDroneOk
                    )
                    Toast.makeText(
                        context,
                        "autoCheck finish $isDroneOk",
                        Toast.LENGTH_LONG
                    ).show()
                    // Rahul Write case here
                }

                override
                fun onError(e: Throwable) {
                    super.onError(e)
                    retryCount++
                    if (retryCount < 3) {
                        getAutoCheckResult(modelType)
                    } else {
                    }
                }
            }.execute()
        }
    }

    fun autoCheck(modelType: ModelType) {
        isDroneCheckFinish = false
        isDroneOk = false

        object : IOUiRunnable<Boolean?>() {
            override fun generateObservable(): Observable<Boolean?>? {
                return mEvoFlyController?.toRx()?.autoSafeCheck(modelType)
            }

            override fun onNext(success: Boolean) {
                super.onNext(success)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        }.execute()
    }

    fun doPrepare() {
        if (flyState != FlyState.None) {
            Toast.makeText(context, "当前状态，不能执行", Toast.LENGTH_LONG).show()
            return
        }
        if (null != missionManager) {
            missionManager!!.prepareMission(
                autelMission,
                filePath,
                object : CallbackWithOneParamProgress<Boolean?> {
                    override fun onProgress(v: Float) {
                        AutelLog.d(TAG, " prepareMission onProgress $v")
                    }

                    override fun onSuccess(p0: Boolean?) {
                        flyState = FlyState.Prepare
                        AutelLog.d("prepareMission success")
                        Toast.makeText(
                            context,
                            "prepare success",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(autelError: AutelError) {
                        AutelLog.d("prepareMission onFailure")
                        Toast.makeText(context, "prepare failed", Toast.LENGTH_LONG)
                            .show()
                    }
                })
        }
    }
}

