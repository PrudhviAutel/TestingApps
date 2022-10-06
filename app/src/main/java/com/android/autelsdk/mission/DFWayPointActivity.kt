package com.android.autelsdk.mission

import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityDfwayPointBinding
import com.android.autelsdk.mission.DFViewModel.FlyState.*
import com.android.autelsdk.util.FileUtils
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.CallbackWithOneParamProgress
import com.autel.common.error.AutelError
import com.autel.common.flycontroller.FlightErrorState
import com.autel.common.flycontroller.ModelType
import com.autel.common.mission.AutelCoordinate3D
import com.autel.common.mission.AutelMission
import com.autel.common.mission.base.DirectionLatLng
import com.autel.common.mission.base.DistanceModel
import com.autel.lib.jniHelper.NativeHelper
import com.autel.lib.jniHelper.PathPlanningResult
import com.autel.util.log.AutelLog
import java.io.File
import java.util.*

class DFWayPointActivity : AppCompatActivity(), View.OnClickListener {
    val viewModel: DFViewModel by viewModels()
    lateinit var binding: ActivityDfwayPointBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDfwayPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.autoCheck.setOnClickListener(this)
        binding.prepare.setOnClickListener(this)
        binding.start.setOnClickListener(this)
        binding.pause.setOnClickListener(this)
        binding.resume.setOnClickListener(this)
        binding.cancel.setOnClickListener(this)
        binding.download.setOnClickListener(this)

        binding.writeMissionTestData.setOnClickListener(this)
        binding.testWaypoint.setOnClickListener(this)
        binding.testMapping.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        var id = p0?.id
        when (id) {
            R.id.autoCheck -> {
                viewModel.autoCheck(ModelType.ALL)
            }

            R.id.prepare -> {
                if (!viewModel.isDroneOk) {
                    Toast.makeText(this@DFWayPointActivity, "飞行器故障，不能执行", Toast.LENGTH_LONG).show()
                    return
                }
                viewModel.doPrepare()
            }

            R.id.start -> {
                if (viewModel.flyState != Prepare) {
                    Toast.makeText(this@DFWayPointActivity, "当前状态，不能执行", Toast.LENGTH_LONG).show()
                    return
                }
                viewModel.missionManager!!.startMission(object :
                    CallbackWithOneParam<Pair<Boolean?, FlightErrorState?>?> {
                    override fun onSuccess(booleanFlightErrorStatePair: Pair<Boolean?, FlightErrorState?>?) {
                        viewModel.flyState = Start
                        //booleanFlightErrorStatePair.first == true 说明可以安全起飞
                        Toast.makeText(
                            this@DFWayPointActivity,
                            "start result " + booleanFlightErrorStatePair?.first,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(autelError: AutelError) {}
                })
            }

            R.id.pause -> {
                if (viewModel.flyState != Start) {
                    Toast.makeText(this@DFWayPointActivity, "当前状态，不能执行", Toast.LENGTH_LONG).show()
                    return
                }
                viewModel.missionManager!!.pauseMission(object : CallbackWithNoParam {
                    override fun onSuccess() {
                        viewModel.flyState = Pause
                        Toast.makeText(
                            this@DFWayPointActivity,
                            "pause success",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(autelError: AutelError) {}
                })
            }

            R.id.resume -> {
                if (viewModel.flyState != Pause) {
                    Toast.makeText(this@DFWayPointActivity, "当前状态，不能执行", Toast.LENGTH_LONG).show()
                    return
                }
                viewModel.missionManager!!.resumeMission(object : CallbackWithNoParam {
                    override fun onSuccess() {
                        viewModel.flyState = Start
                        Toast.makeText(
                            this@DFWayPointActivity,
                            "continue success",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(autelError: AutelError) {}
                })
            }

            R.id.cancel -> {
                if (viewModel.flyState == None) {
                    Toast.makeText(this@DFWayPointActivity, "当前状态，不能执行", Toast.LENGTH_LONG).show()
                    return
                }
                viewModel.missionManager!!.cancelMission(object : CallbackWithNoParam {
                    override fun onSuccess() {
                        Toast.makeText(
                            this@DFWayPointActivity,
                            "cancel success",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(autelError: AutelError) {}
                })
            }

            R.id.download -> {
                if (viewModel.flyState == None) {
                    Toast.makeText(this@DFWayPointActivity, "当前状态，不能执行", Toast.LENGTH_LONG).show()
                    return
                }
                viewModel.missionManager!!.downloadMission(object :
                    CallbackWithOneParamProgress<AutelMission?> {
                    override fun onProgress(v: Float) {}
                    override fun onSuccess(p0: AutelMission?) {
                        Toast.makeText(
                            this@DFWayPointActivity,
                            "download success",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(autelError: AutelError) {}
                })
            }

            R.id.writeMissionTestData -> {
                val myDir: File = File(FileUtils.getMissionFilePath())
                if (!myDir.exists()) {
                    myDir.mkdirs()
                }
                val missionType = 1.0 //任务类型，1-航点任务，6-矩形/多边形任务
                //长度/高度单位均为米

                //长度为3，飞机的纬度、经度、起飞高度
                val droneLocation = doubleArrayOf(22.59638835580453, 113.99613850526757, 40.0)
                //长度为3，返航点的纬度、经度、返航高度
                val homeLocation = doubleArrayOf(22.59638835580453, 113.99613850526757, 50.0)
                //长度为4，上升盘旋点的纬度、经度、高度、盘旋半径
                val launchLocation =
                    doubleArrayOf(22.59638835580453, 113.99318883642341, 100.0, 120.0)
                //长度为4，下降盘旋点的纬度、经度、高度、盘旋半径
                val landingLocation =
                    doubleArrayOf(22.59291695879857, 113.99787910849454, 100.0, 120.0)
                //长度为8（两个点），如果没有可以全设为0，只用于矩形和多边形，矩形/多边形与上升下降盘旋点之间的点的纬度、经度、高度、是否使用该航点(0-使用，1-不使用)
                val avoidPosition = doubleArrayOf(
                    22.598295333564423, 113.99354868480384, 100.0, 1.0,
                    22.598772827314363, 113.99867325644607, 100.0, 1.0
                )
                val waypointLen = 2.toChar() //航点的个数/矩形多边形是顶点的个数
                val poiPointLen = 2 //观察点的个数

                //以下参数针对矩形、多边形任务,航点任务时全置为 0 就可以了
                val UAVTurnRad = 120.0 //飞机转弯半径，默认 120 米
                val UAVFlyVel = 17.0 //飞行速度(单位m/s)
                val UserFPKIsDef = 1.0 //是否用户自定义主航线角度，0-自动，1-手动
                val UserFlyPathA = 0.0 //用户自定义主航线角度，UserFPKIsDef为1时生效
                val WidthSid = 140.56 //旁向扫描宽度,//2*height*tan(HFOV/2)需要自行计算得出
                val OverlapSid = 0.7 //旁向重叠率（0-1）
                val WidthHead = 78.984 //航向扫描宽度,//2*height*tan(VFOV/2)需要自行计算得出
                val OverlapHead = 0.8 //航向重叠率（0-1）
                val UAVFlyAlt = 100.0 //飞行高度

                /*
                    航点定义根据接口协议有16个变量，分别为：
                    变量 0：当前航点标识（目前等于航点在当前任务中的序号）
                    变量 1：当前航点类型，其中：0–普通航点/飞越;1-兴趣点Orbit;4–起飞航点;5–按时间盘旋航点;6-按圈数盘旋航点;7–降落航点
                    变量 2：航点坐标，纬度
                    变量 3：航点坐标，经度
                    变量 4：航点坐标，高度
                    变量 5：航点飞行速度，单位米/秒
                    变量 6：盘旋时间或盘旋圈数，只针对航点类型为盘旋有用
                    变量 7：盘旋半径，单位：米
                    变量 8：盘旋方向：0-顺时针;1-逆时针盘旋
                    变量 9：兴趣点起始角度 1-360度
                    变量10：兴趣点水平角度 1-360度
                    变量11：相机动作类型: 0-无，1-拍照，2-定时拍照，3-定距拍照，4-录像
                    变量12：相机动作参数，定时和定距的参数
                    变量13：相机动作参数，云台俯仰角（-120 -- 0）
                    变量14-15：未定义
                */
                //航点任务
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
                //矩形任务,顶点个数必须大于等于 4 个
//                double[] waypointParamList = new double[]{1.0, 0.0, 22.59808119092429, 113.9951432761672, 100.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -90.0, 0.0, 0.0,
//                        2.0, 0.0, 22.59808119092429, 113.9971040869537, 100.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -90.0, 0.0, 0.0,
//                        3.0, 0.0, 22.596611380444926, 113.9971040869537, 100.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -90.0, 0.0, 0.0,
//                        4.0, 0.0, 22.596611380444926, 113.9951432761672, 100.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -90.0, 0.0, 0.0};

                /*
                    航点定义根据接口协议有17个变量，分别为：
                    变量 0：纬度
                    变量 1：经度
                    变量 2：高度
                    变量 3：半径
                    变量 4：IP_Type，默认 11
                    变量 5：关联航点个数
                */
                val poiParamList = doubleArrayOf(
                    22.601550713371807, 113.99913365283817, 0.0, 120.0, 11.0, 1.0,
                    22.600490797193245, 113.99435713952568, 20.0, 120.0, 11.0, 0.0
                )

                //关联航点序号列表，每个观察点最多关联五个航点，数组个数为观察点个数*5
                val linkPoints = intArrayOf(2, 0, 0, 0, 0, 0, 0, 0, 0, 0)

                //是否使用地形跟随
                val isEnableTopographyFollow = true

                //返回0表示成功，返回非0表示失败
                val res: Int = NativeHelper.writeMissionFile(
                    viewModel.filePath,
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
                    (if (isEnableTopographyFollow) 1
                    else 0.toDouble()) as Double
                )
                AutelLog.d("NativeHelper", " writeMissionFile result -> $res")
            }

            R.id.testWaypoint -> {

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


                /**
                 * waypointParams：航点参数每16个值为一组，以下是以两个航点为例子；
                 * 参数说明：航点定义根据接口协议有16个变量，分别为：
                 * 航点定义根据接口协议有16个变量，分别为：
                 * 变量 0：当前航点标识（目前等于航点在当前任务中的序号）
                 * 变量 1：当前航点类型，其中：0–普通航点/飞越;1-兴趣点Orbit;4–起飞航点;5–按时间盘旋航点;6-按圈数盘旋航点;7–降落航点
                 * 变量 2：航点坐标，纬度
                 * 变量 3：航点坐标，经度
                 * 变量 4：航点坐标，高度
                 * 变量 5：航点飞行速度，单位米/秒
                 * 变量 6：盘旋时间或盘旋圈数，只针对航点类型为盘旋有用
                 * 变量 7：盘旋半径，单位：米
                 * 变量 8：盘旋方向：0-顺时针;1-逆时针盘旋
                 * 变量 9：兴趣点起始角度 1-360度
                 * 变量10：兴趣点水平角度 1-360度
                 * 变量11：相机动作类型: 0-无，1-拍照，2-定时拍照，3-定距拍照，4-录像
                 * 变量12：相机动作参数，定时(单位s)和定距（单位m）的参数
                 * 变量13：相机动作参数，云台俯仰角（-120 -- 0）
                 * 变量14-15：未定义
                 */
                /**
                 * waypointParams：航点参数每16个值为一组，以下是以两个航点为例子；
                 * 参数说明：航点定义根据接口协议有16个变量，分别为：
                 * 航点定义根据接口协议有16个变量，分别为：
                 * 变量 0：当前航点标识（目前等于航点在当前任务中的序号）
                 * 变量 1：当前航点类型，其中：0–普通航点/飞越;1-兴趣点Orbit;4–起飞航点;5–按时间盘旋航点;6-按圈数盘旋航点;7–降落航点
                 * 变量 2：航点坐标，纬度
                 * 变量 3：航点坐标，经度
                 * 变量 4：航点坐标，高度
                 * 变量 5：航点飞行速度，单位米/秒
                 * 变量 6：盘旋时间或盘旋圈数，只针对航点类型为盘旋有用
                 * 变量 7：盘旋半径，单位：米
                 * 变量 8：盘旋方向：0-顺时针;1-逆时针盘旋
                 * 变量 9：兴趣点起始角度 1-360度
                 * 变量10：兴趣点水平角度 1-360度
                 * 变量11：相机动作类型: 0-无，1-拍照，2-定时拍照，3-定距拍照，4-录像
                 * 变量12：相机动作参数，定时(单位s)和定距（单位m）的参数
                 * 变量13：相机动作参数，云台俯仰角（-120 -- 0）
                 * 变量14-15：未定义
                 */
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
            }

            R.id.testMapping -> {
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

            }
        }
    }
}