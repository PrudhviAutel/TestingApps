package com.android.autelsdk.remoteController

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
import com.android.autelsdk.remoteController.fragments.InterfaceDebuggingRCFragment
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.autel.common.remotecontroller.*
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController
import kotlinx.coroutines.runBlocking


// On Activity Start First getCustomViewResId() is called and then onCreate() --- nothing else on Activity Creation
class RemoteControllerActivity : BaseActivity<AutelRemoteController>() {
    val TAG = RemoteControllerActivity::class.java.simpleName

    private val viewModel: RemoteControllerViewModel by viewModels()
    private lateinit var binding: ActivityRemoteControllerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, customViewResId)

        initUi()
        runTests()

        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, InterfaceDebuggingRCFragment())
            .commitNow()

    }

    private fun handleListeners() {

    }

    override fun initController(product: BaseProduct?): AutelRemoteController? {
        if (product != null)
            return product.remoteController

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_remote_controller
    }

    override fun initUi() {
        handleListeners()
    }

    fun runTests() {

        runBlocking {

            for (language in RemoteControllerLanguage.values()) {
                viewModel.setLanguageTest(language)
                    .observe(this@RemoteControllerActivity, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

                viewModel.getLanguageTest().observe(this@RemoteControllerActivity, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                })
            }

            viewModel.enterPairingTest()
                .observe(this@RemoteControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }

                })

            for (rfPower in RFPower.values()) {
                viewModel.setRFPowerTest(rfPower)
                    .observe(this@RemoteControllerActivity, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

                viewModel.getRFPowerTest().observe(this@RemoteControllerActivity, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                })
            }

            for (mode in TeachingMode.values()) {
                viewModel.setTeacherStudentModeTest(mode)
                    .observe(this@RemoteControllerActivity, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

                viewModel.getTeacherStudentModeTest().observe(this@RemoteControllerActivity, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                })
            }

            for (parameterUnit in RemoteControllerParameterUnit.values()) {
                viewModel.setParameterUnitTest(parameterUnit)
                    .observe(this@RemoteControllerActivity, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

                viewModel.getParameterUnitTest().observe(this@RemoteControllerActivity, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                })
            }

            for (mode in RemoteControllerCommandStickMode.values()) {
                viewModel.setRCCommandStickModeTest(mode)
                    .observe(this@RemoteControllerActivity, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

                viewModel.getRCCommandStickModeTest().observe(this@RemoteControllerActivity, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {

                        }
                    }
                })
            }

            viewModel.setYawCoefficientTest(0.3f)
                .observe(this@RemoteControllerActivity, Observer { msg ->

                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {
                            binding.testResults.append(Utils.getColoredText(getString(R.string.not_getting_any_response) + "setYawCoefficient() = ${0.3f}", ""))
                        }
                    }

                })

            viewModel.getYawCoefficientTest().observe(this@RemoteControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {
                        binding.testResults.append(Utils.getColoredText(getString(R.string.not_getting_any_response) + "getYawCoefficient()", ""))
                    }
                }
            })

            viewModel.getVersionInfoTest().observe(this@RemoteControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {
                        binding.testResults.append(Utils.getColoredText(getString(R.string.not_getting_any_response) + "getVersionInfo()", ""))
                    }
                }
            })

            viewModel.getSerialNumberTest().observe(this@RemoteControllerActivity, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                    }
                    else -> {
                        binding.testResults.append(Utils.getColoredText(getString(R.string.not_getting_any_response) + "getSerialNumber()", ""))
                    }
                }
            })

        }


    }



}