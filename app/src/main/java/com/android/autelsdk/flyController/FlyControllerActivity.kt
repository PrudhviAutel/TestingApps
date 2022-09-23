package com.android.autelsdk.flyController

import android.Manifest
import android.os.Bundle
import android.text.Spannable
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityFlyControllerBinding
import com.android.autelsdk.event.ProductConnectEvent
import com.android.autelsdk.flyController.fragments.AircraftStatusDirectCommandFCFragment
import com.android.autelsdk.flyController.fragments.DebugLogFCFragment
import com.android.autelsdk.flyController.fragments.FlightControlParameterReadingFCFragment
import com.android.autelsdk.flyController.fragments.InterfaceDebuggingFCFragment
import com.android.autelsdk.remoteController.fragments.AircraftStatusDirectCommandRCFragment
import com.android.autelsdk.remoteController.fragments.DebugLogRCFragment
import com.android.autelsdk.remoteController.fragments.FlightControlParameterReadingRCFragment
import com.android.autelsdk.remoteController.fragments.InterfaceDebuggingRCFragment
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.ExcelWorkbook
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.autel.common.flycontroller.LedPilotLamp
import com.autel.sdk.flycontroller.AutelFlyController
import com.autel.sdk.product.BaseProduct
import kotlinx.coroutines.runBlocking
import org.apache.poi.ss.usermodel.Workbook
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class FlyControllerActivity : BaseActivity<AutelFlyController>() {
    val TAG = FlyControllerActivity::class.java.simpleName
    lateinit var binding: ActivityFlyControllerBinding
    private val viewModel: FlyControllerViewModel by viewModels()
    var TestArray = arrayOf("one", "two")
    private var ExcelTest = TestArray
    var excelWorkbook: ExcelWorkbook = ExcelWorkbook()
    lateinit  var list : List<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)

        binding = DataBindingUtil.setContentView(this, customViewResId)
        //val viewModel : FlyControllerViewModel<AutelFlyController> = ViewModelProvider.
        requestPermission()
        initUi()
        handleListeners()

        runTests()
//        binding.interfaceDebugAfc.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.blue))
//        supportFragmentManager.beginTransaction()
//            .replace(binding.container.id, InterfaceDebuggingFCFragment())
//            .commitNow()
//
//        binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity,R.color.blue))
//        supportFragmentManager.beginTransaction()
//            .replace(binding.container.id,FlightControlParameterReadingRCFragment())
//            .commitNow()

    }

    private fun deselectAllTabs() {
        binding.interfaceDebugAfc.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.white))
        binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.white))
        binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.white))
        binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.white))
    }

    private fun handleListeners() {

        binding.interfaceDebugAfc.root.setOnClickListener { v->
            deselectAllTabs()
            binding.interfaceDebugAfc.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, InterfaceDebuggingFCFragment())
                .commitNow()
        }

        binding.aircraftStatus.root.setOnClickListener { v->
            deselectAllTabs()
            binding.aircraftStatus.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, AircraftStatusDirectCommandFCFragment())
                .commitNow()
        }

        binding.flightControl.root.setOnClickListener { v->
            deselectAllTabs()
            binding.flightControl.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, FlightControlParameterReadingFCFragment())
                .commitNow()
        }

        binding.debugLog.root.setOnClickListener { v->
            deselectAllTabs()
            binding.debugLog.optionParent.setBackgroundColor(ContextCompat.getColor(this@FlyControllerActivity, R.color.blue))
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, DebugLogFCFragment())
                .commitNow()
        }

    }


    override fun initController(product: BaseProduct?): AutelFlyController? {
        if (product != null)
            return product!!.flyController

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_fly_controller
    }

    override fun initUi() {

    }

    private fun createReport(datalist : List<String>) {
        Log.i("Rl","inside create report function "+datalist)

        excelWorkbook.createExcelWorkbook()
        excelWorkbook.exportDataIntoWorkbook(applicationContext,datalist)
//        excelWorkbook.excel2pdf()
        ///excelWorkbook.storeExcelInStorage(applicationContext,"TestDemo")
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this,
                "Write External Storage permission allows us to save files. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                500
            )
        }
    }

    fun runTests() {

        runBlocking {
            viewModel.setBeginnerModeStateTest(true)
                .observe(this@FlyControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            val message : Spannable  = Utils.getColoredText(msg.data.toString(),Constants.SUCCESS)
                            binding.testResults.append(message)
                           var splitWords : List<String> = message.split(" ")
                            Log.i("Rl","split completed "+splitWords)
                            list = arrayListOf(splitWords[0],splitWords[1],splitWords[2],splitWords[4])
                            Log.i("Rl","assigning completed "+list)
                            createReport(list)
                        }
                        Status.ERROR -> {
                           // binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            val message : Spannable  = Utils.getColoredText(msg.message.toString(),Constants.FAILED)
                            binding.testResults.append(message)
                            var splitWords : List<String> = message.split(" ")
                            Log.i("Rl","split completed "+splitWords)
                            list = arrayListOf(splitWords[0],splitWords[1],splitWords[2],splitWords[4],splitWords[4],splitWords[5])
                            Log.i("Rl","assigning completed "+list)
                            createReport(list)
                        }
                        else -> {

                        }
                    }

                })

            viewModel.getBeginnerModeStateTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setBeginnerModeStateTest(false)
                .observe(this@FlyControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            viewModel.getBeginnerModeStateTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setMaxHeightTest(80.0)
                .observe(this@FlyControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            viewModel.getMaxHeightTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setReturnHeightTest(30.0)
                .observe(this@FlyControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            viewModel.getReturnHeightTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setMaxRangeTest(500.0)
                .observe(this@FlyControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            viewModel.getMaxRangeTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setMaxHorizontalSpeedTest(5.0)
                .observe(this@FlyControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            viewModel.getMaxHorizontalSpeedTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setCalibrateCompassListenerTest()
                .observe(this@FlyControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            viewModel.getSerialNumberTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.getVersionInfoTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.cancelLandTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.cancelReturnTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.goHomeTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.landTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.startCalibrateCompassTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setAircraftLocationAsHomePointTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setLocationAsHomePointTest(0.0,0.0).observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            for (ledPilotLamp in LedPilotLamp.values()) {
                viewModel.setLedPilotLampTest(ledPilotLamp).observe(this@FlyControllerActivity, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                })

                viewModel.getLedPilotLampTest().observe(this@FlyControllerActivity, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                })
            }

            viewModel.setAttitudeModeEnableTest(true).observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setAttitudeModeEnableTest(false).observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.isAttitudeModeEnableTest().observe(this@FlyControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.data.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })


        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProductConnectEvent(event: ProductConnectEvent?) {
        if (event != null) {
        }
    }

}




