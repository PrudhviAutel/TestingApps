package com.android.autelsdk.flyController

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityFlyControllerBinding
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.ExcelWorkbook
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.autel.sdk.flycontroller.AutelFlyController
import com.autel.sdk.product.BaseProduct
import kotlinx.coroutines.runBlocking


class FlyControllerActivity : BaseActivity<AutelFlyController>() {
    val TAG = FlyControllerActivity::class.java.simpleName
    lateinit var binding: ActivityFlyControllerBinding
    private val viewModel: FlyControllerViewModel by viewModels()
    var TestArray = arrayOf("one", "two")
    private var ExcelTest = TestArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, customViewResId)
        //val viewModel : FlyControllerViewModel<AutelFlyController> = ViewModelProvider.
        requestPermission()
        initUi()
        //createReport()
        runTests()

    }

    private fun handleListeners() {
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

    private fun createReport() {
        val excelWorkbook: ExcelWorkbook = ExcelWorkbook()
        excelWorkbook.createExcelWorkbook()
        excelWorkbook.exportDataIntoWorkbook(applicationContext)
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

            viewModel.setHorizontalSpeedTest(5.0)
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

            viewModel.getHorizontalSpeedTest().observe(this@FlyControllerActivity, Observer { msg ->
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

            viewModel.setBeginnerModeStateTest(true)
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
        }
    }


}




