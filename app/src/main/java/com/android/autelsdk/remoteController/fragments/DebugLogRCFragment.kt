package com.android.autelsdk.remoteController.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.autelsdk.R
import com.android.autelsdk.databinding.FragmentDebugLogControlRcBinding
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.autel.common.remotecontroller.*

class DebugLogRCFragment : Fragment() {

    private lateinit var binding: FragmentDebugLogControlRcBinding
    private val viewModel : RemoteControllerViewModel by activityViewModels()

    companion object {
        fun newInstance() = DebugLogRCFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_debug_log_control_rc , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runTests()
    }

    fun runTests() {

        lifecycleScope.launchWhenStarted {

            for (language in RemoteControllerLanguage.values()) {
                viewModel.setLanguageTest(language)
                    .observe(viewLifecycleOwner, Observer { msg ->

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

                viewModel.getLanguageTest().observe(viewLifecycleOwner, Observer { msg ->
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
                .observe(viewLifecycleOwner, Observer { msg ->

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
                    .observe(viewLifecycleOwner, Observer { msg ->

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

                viewModel.getRFPowerTest().observe(viewLifecycleOwner, Observer { msg ->
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
                    .observe(viewLifecycleOwner, Observer { msg ->

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

                viewModel.getTeacherStudentModeTest().observe(viewLifecycleOwner, Observer { msg ->
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
                    .observe(viewLifecycleOwner, Observer { msg ->

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

                viewModel.getParameterUnitTest().observe(viewLifecycleOwner, Observer { msg ->
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
                    .observe(viewLifecycleOwner, Observer { msg ->

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

                viewModel.getRCCommandStickModeTest().observe(viewLifecycleOwner, Observer { msg ->
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

            for (calibration in RemoteControllerStickCalibration.values()) {
                viewModel.setStickCalibrationTest(calibration)
                    .observe(viewLifecycleOwner, Observer {msg ->
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
                .observe(viewLifecycleOwner, Observer { msg ->

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

            viewModel.getYawCoefficientTest().observe(viewLifecycleOwner, Observer { msg ->
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

            viewModel.getVersionInfoTest().observe(viewLifecycleOwner, Observer { msg ->
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

            viewModel.getSerialNumberTest().observe(viewLifecycleOwner, Observer { msg ->
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