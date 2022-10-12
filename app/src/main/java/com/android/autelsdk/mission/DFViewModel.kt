package com.android.autelsdk.mission

import android.content.Context
import android.util.Pair
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.TestApplication
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.rxrunnable.IOUiRunnable
import com.android.autelsdk.util.FileUtils
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.CallbackWithOneParamProgress
import com.autel.common.battery.cruiser.CruiserBatteryInfo
import com.autel.common.error.AutelError
import com.autel.common.flycontroller.AutoSafeState
import com.autel.common.flycontroller.FlightErrorState
import com.autel.common.flycontroller.ModelType
import com.autel.common.flycontroller.SafeCheck
import com.autel.common.flycontroller.cruiser.CruiserFlyControllerInfo
import com.autel.common.mission.AutelCoordinate3D
import com.autel.common.mission.AutelMission
import com.autel.common.mission.MissionType
import com.autel.common.mission.RealTimeInfo
import com.autel.common.mission.base.DirectionLatLng
import com.autel.common.mission.base.DistanceModel
import com.autel.common.mission.cruiser.CruiserWaypointFinishedAction
import com.autel.common.mission.cruiser.CruiserWaypointMission
import com.autel.common.product.AutelProductType
import com.autel.common.remotecontroller.RemoteControllerInfo
import com.autel.internal.battery.cruiser.CruiserBatteryImpl
import com.autel.internal.flycontroller.cruiser.CruiserFlyControllerImpl
import com.autel.internal.sdk.mission.cruiser.CruiserWaypointRealTimeInfoImpl
import com.autel.lib.jniHelper.NativeHelper
import com.autel.lib.jniHelper.PathPlanningResult
import com.autel.sdk.battery.CruiserBattery
import com.autel.sdk.flycontroller.CruiserFlyController
import com.autel.sdk.mission.MissionManager
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController
import com.autel.sdk10.utils.BytesUtils
import com.autel.util.log.AutelLog
import io.reactivex.Observable
import java.io.File
import java.util.*

class DFViewModel : ViewModel() {

    lateinit var context: Context
    var flyState = FlyState.None
    var result: MutableLiveData<HarnessResult> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val filePath = FileUtils.getMissionFilePath().toString() + "mission.aut"
    var isDroneCheckFinish = false
    var isDroneOk = false
    var TAG = "DFWayPointActivity"

    private var autelMission: CruiserWaypointMission = CruiserWaypointMission()
    var mEvoFlyController: CruiserFlyController? = CruiserFlyControllerImpl()
    var battery: CruiserBattery? = CruiserBatteryImpl()
    var remoteController: AutelRemoteController? = null
    var missionManager: MissionManager? = null

    enum class FlyState {
        Prepare, Start, Pause, None
    }

    lateinit var product: BaseProduct
    fun setProducts(product: BaseProduct) {
        this.product = product
    }

    fun initializeData() {
        // val product: BaseProduct = (context as TestApplication).currentProduct
        if (this::product.isInitialized) {
            if (null != product && product.type == AutelProductType.DRAGONFISH) {
                if (product != null)
                    missionManager = product.missionManager
                missionManager?.setRealTimeInfoListener(object :
                    CallbackWithOneParam<RealTimeInfo> {
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

            if (product != null)
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
            if (product != null)
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
            if (product != null)
                remoteController = product.remoteController
            remoteController?.setInfoDataListener(object :
                CallbackWithOneParam<RemoteControllerInfo?> {
                override fun onSuccess(remoteControllerInfo: RemoteControllerInfo?) {
                    //isImageTransOk = remoteControllerInfo.getDSPPercentage() >= 30;
                }

                override fun onFailure(autelError: AutelError) {}
            })
            AutelLog.d("init missionManager$missionManager")
            initData()
        } else {
            Toast.makeText(context, "Product not connected", Toast.LENGTH_LONG).show()
        }
    }

    fun setMissionManager(): HarnessResult {
        lateinit var harnessResult: HarnessResult
        if (this::product.isInitialized) {
            if (null != product && product.type == AutelProductType.DRAGONFISH) {
                if (product != null)
                    missionManager = product.missionManager
                harnessResult = HarnessResult("setMissionManager() Mission Manager Set", true)
            } else {
                harnessResult = HarnessResult("setMissionManager() product not connected", false)
            }
        } else {
            harnessResult = HarnessResult("setMissionManager() product not connected", false)
        }
        return harnessResult
    }

    fun setBattery(): HarnessResult {
        lateinit var harnessResult: HarnessResult
        if (this::product.isInitialized) {
            if (null != product && product.type == AutelProductType.DRAGONFISH) {
                if (product != null)
                    battery = product.battery as CruiserBattery
                harnessResult = HarnessResult("setBattery() Battery Set", true)
            } else {
                harnessResult = HarnessResult("setBattery() product not connected", false)
            }
        } else {
            harnessResult = HarnessResult("setBattery() product not connected", false)
        }
        return harnessResult
    }

    fun setFlyController(): HarnessResult {
        lateinit var harnessResult: HarnessResult
        if (this::product.isInitialized) {
            if (null != product && product.type == AutelProductType.DRAGONFISH) {
                if (product != null)
                    mEvoFlyController = product.flyController as CruiserFlyController
                harnessResult = HarnessResult("setFlyController() Fly Controller Set", true)
            } else {
                harnessResult = HarnessResult("setFlyController() product not connected", false)
            }
        } else {
            harnessResult = HarnessResult("setFlyController() product not connected", false)
        }
        return harnessResult
    }

    fun setRemoteControllers(): HarnessResult {
        lateinit var harnessResult: HarnessResult
        if (this::product.isInitialized) {
            if (null != product && product.type == AutelProductType.DRAGONFISH) {
                if (product != null)
                    remoteController = product.remoteController
                harnessResult = HarnessResult("setRemoteControllers() Remote Controller Set", true)
            } else {
                harnessResult = HarnessResult("setRemoteControllers() product not connected", false)
            }
        } else {
            harnessResult = HarnessResult("setRemoteControllers() product not connected", false)
        }
        return harnessResult
    }

    fun setMissionManagerListener(): HarnessResult {
        lateinit var harnessResult: HarnessResult
        if (this::product.isInitialized) {
            if (null != product && product.type == AutelProductType.DRAGONFISH) {
                if (product != null)
                    missionManager = product.missionManager
                missionManager?.setRealTimeInfoListener(object :
                    CallbackWithOneParam<RealTimeInfo> {
                    override fun onSuccess(realTimeInfo: RealTimeInfo) {
                        val info = realTimeInfo as CruiserWaypointRealTimeInfoImpl
                        AutelLog.d(
                            "MissionRunning",
                            "timeStamp:" + info.timeStamp + ",speed:" + info.speed + ",isArrived:" + info.isArrived +
                                    ",isDirecting:" + info.isDirecting + ",waypointSequence:" + info.waypointSequence + ",actionSequence:" + info.actionSequence +
                                    ",photoCount:" + info.photoCount + ",MissionExecuteState:" + info.executeState + ",missionID:" + info.missionID
                        )
                        harnessResult = HarnessResult(
                            "setMissionManagerListener() timeStamp:" + info.timeStamp + ",speed:" + info.speed + ",isArrived:" + info.isArrived +
                                    ",isDirecting:" + info.isDirecting + ",waypointSequence:" + info.waypointSequence + ",actionSequence:" + info.actionSequence +
                                    ",photoCount:" + info.photoCount + ",MissionExecuteState:" + info.executeState + ",missionID:" + info.missionID,
                            true
                        )
                    }

                    override fun onFailure(autelError: AutelError) {
                        harnessResult = HarnessResult(
                            "setMissionManagerListener() " + autelError.description,
                            false
                        )
                    }
                })
            } else {
                harnessResult =
                    HarnessResult("setMissionManagerListener() product not connected", false)
            }
        } else {
            harnessResult =
                HarnessResult("setMissionManagerListener() product not connected", false)
        }

        return harnessResult
    }

    fun setBatteryManagerListener(): HarnessResult {
        lateinit var harnessResult: HarnessResult
        if (this::product.isInitialized) {
            if (null != product && product.type == AutelProductType.DRAGONFISH) {
                if (product != null)
                    battery = product?.battery as CruiserBattery
                battery?.setBatteryStateListener(object : CallbackWithOneParam<CruiserBatteryInfo> {
                    override fun onSuccess(batteryState: CruiserBatteryInfo) {
                        AutelLog.d(" batteryState " + batteryState.remainingPercent)
                        harnessResult =
                            HarnessResult(
                                "setBatteryManagerListener() batteryState " + batteryState.remainingPercent,
                                true
                            )
                    }

                    override fun onFailure(autelError: AutelError) {
                        harnessResult = HarnessResult(
                            "setBatteryManagerListener()" + autelError.description,
                            false
                        )
                    }
                })
            } else {
                harnessResult =
                    HarnessResult("setBatteryManagerListener() product not connected", false)
            }
        } else {
            harnessResult =
                HarnessResult("setBatteryManagerListener() product not connected", false)
        }

        return harnessResult
    }

    fun getLowBatteryNotifyThreshold(): HarnessResult {
        lateinit var harnessResult: HarnessResult
        if (this::product.isInitialized) {
            if (null != product && product.type == AutelProductType.DRAGONFISH) {
                if (product != null)
                    battery = product.battery as CruiserBattery
                battery?.getLowBatteryNotifyThreshold(object : CallbackWithOneParam<Float?> {
                    override fun onSuccess(aFloat: Float?) {
                        //  lowBatteryPercent = aFloat;
                        harnessResult = HarnessResult(
                            "getLowBatteryNotifyThreshold()  data :   + $aFloat",
                            true
                        )
                    }

                    override fun onFailure(autelError: AutelError) {
                        harnessResult = HarnessResult("" + autelError.description, false)
                    }
                })
            } else {
                harnessResult =
                    HarnessResult("getLowBatteryNotifyThreshold() product not connected", false)
            }
        } else {
            harnessResult =
                HarnessResult("getLowBatteryNotifyThreshold() product not connected", false)
        }

        return harnessResult
    }

    fun setContexts(context: Context) {
        this.context = context
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
                    /*Toast.makeText(
                        context,
                        "autoCheck finish $isDroneOk",
                        Toast.LENGTH_LONG
                    ).show()*/
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

    fun doPrepare(): HarnessResult {
        lateinit var harnessResult: HarnessResult
        if (flyState != FlyState.None) {
            /*  Toast.makeText(
                  context,
                  "Current state, cannot be executed", Toast.LENGTH_LONG
              ).show()*/
            return HarnessResult("doPrepare() Current state, cannot be executed", false)
        }
        if (null != missionManager) {
            missionManager!!.prepareMission(
                autelMission,
                filePath,
                object : CallbackWithOneParamProgress<Boolean?> {
                    override fun onProgress(v: Float) {
                        AutelLog.d(TAG, " prepareMission onProgress $v")
                        harnessResult =
                            HarnessResult("doPrepare() prepareMission onProgress $v", true)
                    }

                    override fun onSuccess(p0: Boolean?) {
                        flyState = FlyState.Prepare
                        AutelLog.d("prepareMission success")
                        /* Toast.makeText(
                             context,
                             "prepare success",
                             Toast.LENGTH_LONG
                         ).show()*/
                        harnessResult = HarnessResult("doPrepare() prepare success", true)
                    }

                    override fun onFailure(autelError: AutelError) {
                        AutelLog.d("prepareMission onFailure")
                        /*  Toast.makeText(context, "prepare failed", Toast.LENGTH_LONG)
                              .show()*/
                        harnessResult = HarnessResult("doPrepare() prepare failed", false)
                    }
                })
        } else {
            harnessResult = HarnessResult("doPrepare() product is not connected", false)
        }
        return harnessResult
    }

    fun writeMissionTestData(): HarnessResult {

        val myDir: File = File(FileUtils.getMissionFilePath())
        if (!myDir.exists()) {
            myDir.mkdirs()
        }
        val missionType = 1.0 //任务类型，1-航点任务，6-矩形/多边形任务
        val droneLocation = doubleArrayOf(22.59638835580453, 113.99613850526757, 40.0)
        val homeLocation = doubleArrayOf(22.59638835580453, 113.99613850526757, 50.0)
        val launchLocation =
            doubleArrayOf(22.59638835580453, 113.99318883642341, 100.0, 120.0)
        val landingLocation =
            doubleArrayOf(22.59291695879857, 113.99787910849454, 100.0, 120.0)
        val avoidPosition = doubleArrayOf(
            22.598295333564423, 113.99354868480384, 100.0, 1.0,
            22.598772827314363, 113.99867325644607, 100.0, 1.0
        )
        val waypointLen = 2.toChar() //航点的个数/矩形多边形是顶点的个数
        val poiPointLen = 2 //观察点的个数
        val UAVTurnRad = 120.0 //飞机转弯半径，默认 120 米
        val UAVFlyVel = 17.0 //飞行速度(单位m/s)
        val UserFPKIsDef = 1.0 //是否用户自定义主航线角度，0-自动，1-手动
        val UserFlyPathA = 0.0 //用户自定义主航线角度，UserFPKIsDef为1时生效
        val WidthSid = 140.56 //旁向扫描宽度,//2*height*tan(HFOV/2)需要自行计算得出
        val OverlapSid = 0.7 //旁向重叠率（0-1）
        val WidthHead = 78.984 //航向扫描宽度,//2*height*tan(VFOV/2)需要自行计算得出
        val OverlapHead = 0.8 //航向重叠率（0-1）
        val UAVFlyAlt = 100.0 //飞行高度

        val waypointParamList = doubleArrayOf(
            1.0,
            0.0,
            22.597737289727164,
            113.9974874391902,
            100.0,
            17.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            -90.0,
            0.0,
            0.0,
            2.0,
            0.0,
            22.59897542587946,
            114.00336684129968,
            100.0,
            17.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            -90.0,
            0.0,
            0.0
        )
        val poiParamList = doubleArrayOf(
            22.601550713371807, 113.99913365283817, 0.0, 120.0, 11.0, 1.0,
            22.600490797193245, 113.99435713952568, 20.0, 120.0, 11.0, 0.0
        )
        val linkPoints = intArrayOf(2, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val isEnableTopographyFollow = true

        val res: Int = NativeHelper.writeMissionFile(
            filePath,
            missionType,
            droneLocation,
            homeLocation,
            launchLocation,
            landingLocation,
            avoidPosition,
            UAVTurnRad,
            UAVFlyVel,
            UserFPKIsDef,
            UserFlyPathA,
            WidthSid,
            OverlapSid,
            WidthHead,
            OverlapHead,
            UAVFlyAlt,
            waypointLen,
            waypointParamList,
            poiPointLen,
            poiParamList,
            linkPoints,
            (if (isEnableTopographyFollow) 1.0
            else 0.0)
        )

        AutelLog.d("NativeHelper", " writeMissionFile result -> $res")
        return HarnessResult("writeMissionTestData() result -> $res", true)
    }

    fun testWaypoint(): HarnessResult {

        //飞机当前位置
        val drone = doubleArrayOf(22.59651, 113.9972969, 0.0) //经纬高

        //返航点位置
        //返航点位置
        val homePoint = doubleArrayOf(22.59651, 113.9972969, 100.0) //经纬高

        //上升盘旋点
        //上升盘旋点
        val upHomePoint =
            doubleArrayOf(22.59651, 113.99434723115584, 100.0, 120.0) //经、纬、高、盘旋半径

        //下降盘旋点
        //下降盘旋点
        val downHomePoint =
            doubleArrayOf(22.59651, 114.00024656884415, 100.0, 120.0) //经、纬、高、盘旋半径

        val waypointParams = doubleArrayOf(
            1.0,
            0.0,
            22.59794923247847,
            113.9946704742452,
            100.0,
            17.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            2.0,
            0.0,
            22.593907884795755,
            113.99646218984662,
            100.0,
            17.0,
            1.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0
        )
        var result = NativeHelper.getWaypointMissionPath(
            drone,
            homePoint,
            upHomePoint,
            downHomePoint,
            waypointParams
        )
        var errorCode = result.errorCode //是否规划任务成功，0-成功，1-失败

        var flyLength = result.flyLength //航线总距离

        var flyTime = result.flyTime //预计飞行总时间

        var pictNum = result.pictNum //预计拍照数量

        var optCourseAngle = result.optCourseAngle //自动规划主航线角度时使用的主航线角度

        var latLngList = result.latLngList //整条航线所有点的纬经高

        var directionLatLngList =
            result.directionLatLngList //航线中箭头的纬经度

        var distanceModelList =
            result.distanceModelList //航线中两个航点的距离的显示位置的纬度、经度、距离

        var plusList = result.plusList //两个航点间加号的纬度、经度

        AutelLog.debug_i(
            "NativeHelper:", "flyTime = " + flyTime
                    + ", flyLength = " + flyLength + ", picNum = " + pictNum
                    + ",errorCode = " + errorCode
        )

        return HarnessResult(
            "testWaypoint() flyTime = " + flyTime
                    + ", flyLength = " + flyLength + ", picNum = " + pictNum
                    + ",errorCode = " + errorCode, true
        )
    }

    fun testMapping(): HarnessResult {
        val drone = doubleArrayOf(22.59651, 113.9972969, 0.0) //纬经高
        //返航点位置
        val homePoint = doubleArrayOf(22.59651, 113.9972969, 100.0) //纬经高
        //上升盘旋点
        val upHomePoint =
            doubleArrayOf(22.59651, 113.99434723115584, 100.0, 120.0) //纬、经、高、盘旋半径
        //下降盘旋点
        val downHomePoint =
            doubleArrayOf(22.59651, 114.00024656884415, 100.0, 120.0) //纬、经、高、盘旋半径
        //途经点1 （上升盘旋点到任务之间添加）
        val startAvoid = doubleArrayOf(
            22.595300191562032,
            113.98885025388489,
            100.0,
            1.0
        ) //纬、经、高、是否有效（0-无效，1-有效）
        //途经点2
        val endAvoid = doubleArrayOf(
            22.592050563109837,
            113.99623427307421,
            100.0,
            1.0
        ) //纬、经、高、是否有效（0-无效，1-有效）

        //长度为8（两个点），如果没有可以全设为0，只用于矩形和多边形，矩形/多边形与上升下降盘旋点之间的点的纬度、经度、高度、是否使用该航点(0-使用，1-不使用)
        val avoidPoints = Arrays.copyOf(startAvoid, startAvoid.size + endAvoid.size)
        //将b数组添加到已经含有a数组的c数组中去
        System.arraycopy(endAvoid, 0, avoidPoints, startAvoid.size, endAvoid.size)
        //矩形或多边形顶点坐标(经、纬、高)
        val vertexs = doubleArrayOf(
            22.603459238667625,
            113.99525530891242,
            100.0,
            22.603459238667625,
            113.9972294147372,
            100.0,
            22.601993332010267,
            113.9972294147372,
            100.0,
            22.601993332010267,
            113.99525530891242,
            100.0
        )
        //航线高度
        val height = 100f
        //航线速度
        val speed = 17.0f
        //旁向重叠率
        val sideRate = 0.8
        //主航线重叠率
        val courseRate = 0.7f
        //主航线角度 0:自动，1：用户自定义航向角度
        val userDefineAngle = 0
        //当userDefineAngle为1时有效
        val courseAngle = 30
        //飞机转弯半径，默认要设置120
        val turningRadius = 120
        //旁向扫描宽度
        val sideScanWidth = 140.56235 //2*height*tan(HFOV/2)需要自行计算得出
        //航向扫描宽度
        val courseScanWidth = 78.98377 //2*height*tan(VFOV/2)
        val result: PathPlanningResult = NativeHelper.getMappingMissionPath(
            drone,
            homePoint,
            upHomePoint,
            downHomePoint,
            vertexs,
            avoidPoints,
            height.toDouble(),
            speed.toDouble(),
            sideRate,
            courseRate
                .toDouble(),
            userDefineAngle.toDouble(),
            courseAngle.toDouble(),
            turningRadius
                .toDouble(),
            sideScanWidth,
            courseScanWidth
        )
        val area: Double = result.area //矩形，多边形的面积
        val flyLength: Double = result.flyLength //航线总距离
        val flyTime: Double = result.flyTime //预计飞行总时间
        val pictNum: Double = result.pictNum //预计拍照数量
        val optCourseAngle: Double = result.optCourseAngle //自动规划主航线角度时使用的主航线角度
        val whiteLatLngList: List<AutelCoordinate3D> =
            result.whiteLatLngList //矩形/多边形区域内转折点的纬经高
        val latLngList: List<AutelCoordinate3D> = result.latLngList //整条航线所有点的纬经高
        val directionLatLngList: List<DirectionLatLng> =
            result.directionLatLngList //航线中箭头的纬经度
        val distanceModelList: List<DistanceModel> =
            result.distanceModelList //航线中两个航点的距离的显示位置的纬度、经度、距离
        val plusList: List<AutelCoordinate3D> = result.plusList //两个航点间加号的纬度、经度
        AutelLog.d("NativeHelper", " result " + result.area + " " + result.errorCode)

        return HarnessResult(
            "testMapping() : Area = " + result.area + " Result Code =" + result.errorCode,
            true
        )
    }

    fun download(): HarnessResult {
        if (flyState == FlyState.None) {
            /* Toast.makeText(context, "Current state, cannot be executed", Toast.LENGTH_LONG).show()*/
            return HarnessResult("download() downloadCurrent state, cannot be executed", false)
        }

        lateinit var harnessResult: HarnessResult
        if (null != missionManager) {
            missionManager!!.downloadMission(object :
                CallbackWithOneParamProgress<AutelMission?> {
                override fun onProgress(v: Float) {}
                override fun onSuccess(p0: AutelMission?) {
                    /*Toast.makeText(
                        context,
                        "download success",
                        Toast.LENGTH_LONG
                    ).show()*/

                    harnessResult = HarnessResult(
                        "download() success", true
                    )
                }

                override fun onFailure(autelError: AutelError) {
                    harnessResult = HarnessResult(
                        "download() " + autelError.description, false
                    )
                }
            })
        } else {
            harnessResult = HarnessResult("download() product is not connected", false)
        }
        return harnessResult
    }

    fun cancel(): HarnessResult {
        if (flyState == FlyState.None) {
            /* Toast.makeText(context, "Current state, cannot be executed", Toast.LENGTH_LONG).show()*/
            return HarnessResult("cancel() Current state, cannot be executed", false)
        }

        lateinit var harnessResult: HarnessResult
        if (null != missionManager) {
            missionManager!!.cancelMission(object : CallbackWithNoParam {
                override fun onSuccess() {
                    /*Toast.makeText(
                        context,
                        "cancel success",
                        Toast.LENGTH_LONG
                    ).show()*/
                    harnessResult = HarnessResult(
                        "cancel() success", true
                    )
                }

                override fun onFailure(autelError: AutelError) {
                    harnessResult = HarnessResult(
                        "cancel() " + autelError.description, false
                    )
                }
            })
        } else {
            harnessResult = HarnessResult("cancel() product is not connected", false)
        }

        return harnessResult
    }

    fun resume(): HarnessResult {
        if (flyState != FlyState.Pause) {
            /* Toast.makeText(context, "Current state, cannot be executed", Toast.LENGTH_LONG).show()*/
            return HarnessResult("resume() Current state, cannot be executed", false)
        }
        lateinit var harnessResult: HarnessResult
        if (null != missionManager) {
            missionManager!!.resumeMission(object : CallbackWithNoParam {
                override fun onSuccess() {
                    flyState = FlyState.Start
                    /*  Toast.makeText(
                          context,
                          "continue success",
                          Toast.LENGTH_LONG
                      ).show()*/
                    harnessResult = HarnessResult(
                        "resume() continue success", true
                    )
                }

                override fun onFailure(autelError: AutelError) {
                    harnessResult = HarnessResult(
                        "resume() " + autelError.description, false
                    )
                }
            })
        } else {
            harnessResult = HarnessResult("resume() product is not connected", false)
        }
        return harnessResult
    }

    fun pause(): HarnessResult {
        if (flyState != FlyState.Start) {
            /*  Toast.makeText(context, "Current state, cannot be executed", Toast.LENGTH_LONG).show()*/
            return HarnessResult("pause() Current state, cannot be executed", false)
        }
        lateinit var harnessResult: HarnessResult
        if (null != missionManager) {
            missionManager!!.pauseMission(object : CallbackWithNoParam {
                override fun onSuccess() {
                    flyState = FlyState.Pause
                    /*   Toast.makeText(
                           context,
                           "pause success",
                           Toast.LENGTH_LONG
                       ).show()*/
                    harnessResult = HarnessResult(
                        "pause() success", true
                    )
                }

                override fun onFailure(autelError: AutelError) {
                    harnessResult = HarnessResult(
                        "pause()" + autelError.description, false
                    )
                }
            })
        } else {
            harnessResult = HarnessResult("pause() product is not connected", false)
        }
        return harnessResult
    }

    fun start(): HarnessResult {
        if (flyState != FlyState.Prepare) {
            /*  Toast.makeText(context, "Current state, cannot be executed", Toast.LENGTH_LONG).show()*/
            return HarnessResult("start() Current state, cannot be executed", false)
        }
        lateinit var harnessResult: HarnessResult
        if (null != missionManager) {
            missionManager!!.startMission(object :
                CallbackWithOneParam<Pair<Boolean?, FlightErrorState?>?> {
                override fun onSuccess(booleanFlightErrorStatePair: Pair<Boolean?, FlightErrorState?>?) {
                    flyState = FlyState.Start
                    //booleanFlightErrorStatePair.first == true 说明可以安全起飞
                    /*  Toast.makeText(
                          context,
                          "start result " + booleanFlightErrorStatePair?.first,
                          Toast.LENGTH_LONG
                      ).show()*/
                    harnessResult = HarnessResult(
                        "start() success", true
                    )
                }

                override fun onFailure(autelError: AutelError) {
                    harnessResult = HarnessResult(
                        "start() " + autelError.description, false
                    )
                }
            })
        } else {
            harnessResult = HarnessResult("start() product is not connected", false)
        }
        return harnessResult
    }
}

