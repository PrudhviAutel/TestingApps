package com.android.autelsdk.flyController.fragments

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
import com.android.autelsdk.databinding.FragmentDebugLogControlFcBinding
import com.android.autelsdk.databinding.FragmentDebugLogControlRcBinding
import com.android.autelsdk.flyController.FlyControllerViewModel
import com.android.autelsdk.remoteController.RemoteControllerViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.autel.common.flycontroller.LedPilotLamp
import com.autel.common.remotecontroller.*

class DebugLogFCFragment : Fragment() {

    private lateinit var binding: FragmentDebugLogControlFcBinding
    private val viewModel : FlyControllerViewModel by activityViewModels()

    companion object {
        fun newInstance() = DebugLogFCFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_debug_log_control_fc , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runTests()
    }

    fun runTests() {
        lifecycleScope.launchWhenStarted {
        val boolean = arrayListOf<Boolean>(true,false)
            binding.DelayText.visibility = View.VISIBLE
        for (state in boolean) {
                viewModel.setBeginnerModeStateTest(state)
                    .observe(viewLifecycleOwner, Observer { msg ->

                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.DelayText.visibility = View.GONE
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.DelayText.visibility = View.GONE
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {

                            }
                        }

                    })

            viewModel.getBeginnerModeStateTest().observe(viewLifecycleOwner, Observer { msg ->
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

            for (state in boolean) {
                viewModel.setAttitudeModeEnableTest(state)
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
            }

            for (state in LedPilotLamp.values()) {
                viewModel.setLedPilotLampTest(state)
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

                viewModel.getLedPilotLampTest().observe(viewLifecycleOwner, Observer { msg ->
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

            viewModel.setMaxHeightTest(0.3)
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

            viewModel.getMaxHeightTest().observe(viewLifecycleOwner, Observer { msg ->
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

            viewModel.setMaxRangeTest(0.3)
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

            viewModel.getMaxRangeTest().observe(viewLifecycleOwner, Observer { msg ->
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

            viewModel.setReturnHeightTest(0.3)
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

            viewModel.getReturnHeightTest().observe(viewLifecycleOwner, Observer { msg ->
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

            viewModel.setMaxHorizontalSpeedTest(0.3)
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

            viewModel.getMaxHorizontalSpeedTest().observe(viewLifecycleOwner, Observer { msg ->
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

            viewModel.setLocationAsHomePointTest(0.3,0.3)
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

            viewModel.goHomeTest()
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

            viewModel.cancelReturnTest()
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

            viewModel.cancelLandTest()
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

            viewModel.setAircraftLocationAsHomePointTest()
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

            viewModel.landTest()
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

            viewModel.startCalibrateCompassTest()
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

            viewModel.getVersionInfoTest()
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

            viewModel.getSerialNumberTest()
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


    }
        }}