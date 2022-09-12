package com.android.autelsdk.remoteController.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.autelsdk.R
import com.android.autelsdk.databinding.FragmentInterfaceDebuggingRcBinding
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.remotecontroller.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class InterfaceDebuggingRCFragment : Fragment() {

    private lateinit var binding: FragmentInterfaceDebuggingRcBinding

    companion object {
        fun newInstance() = InterfaceDebuggingRCFragment()
    }

    private lateinit var viewModel: RemoteControllerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interface_debugging_rc , container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RemoteControllerViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runInterfaceTests()
    }

    fun runInterfaceTests() {

        lifecycleScope.launch(Dispatchers.Main) {
            runBlocking {

                for (language in RemoteControllerLanguage.values()) {
                    viewModel.setLanguageTest(language)
                        .observeOnce(viewLifecycleOwner, Observer { msg ->

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

                    viewModel.getLanguageTest().observeOnce(viewLifecycleOwner, Observer { msg ->
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
                    .observeOnce(viewLifecycleOwner, Observer { msg ->

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
                        .observeOnce(viewLifecycleOwner, Observer { msg ->

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

                    viewModel.getRFPowerTest().observeOnce(viewLifecycleOwner, Observer { msg ->
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
                        .observeOnce(viewLifecycleOwner, Observer { msg ->

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

                    viewModel.getTeacherStudentModeTest().observeOnce(viewLifecycleOwner, Observer { msg ->
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
                        .observeOnce(viewLifecycleOwner, Observer { msg ->

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

                    viewModel.getParameterUnitTest().observeOnce(viewLifecycleOwner, Observer { msg ->
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
                        .observeOnce(viewLifecycleOwner, Observer { msg ->

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

                    viewModel.getRCCommandStickModeTest().observeOnce(viewLifecycleOwner, Observer { msg ->
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
                        .observeOnce(viewLifecycleOwner, Observer {msg ->
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
                    .observeOnce(viewLifecycleOwner, Observer { msg ->

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

                viewModel.getYawCoefficientTest().observeOnce(viewLifecycleOwner, Observer { msg ->
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

                viewModel.getVersionInfoTest().observeOnce(viewLifecycleOwner, Observer { msg ->
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

                viewModel.getSerialNumberTest().observeOnce(viewLifecycleOwner, Observer { msg ->
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


}