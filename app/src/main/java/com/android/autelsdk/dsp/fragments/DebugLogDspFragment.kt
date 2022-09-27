package com.android.autelsdk.gimbal.fragments

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
import com.android.autelsdk.databinding.FragmentDebugLogControlGimbalBinding
import com.android.autelsdk.gimbal.GimbalViewModel
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Status
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce
import com.autel.common.gimbal.GimbalAxisType
import com.autel.common.gimbal.GimbalWorkMode

class DebugLogDspFragment : Fragment() {

    private lateinit var binding: FragmentDebugLogControlGimbalBinding
    private val viewModel : GimbalViewModel by activityViewModels()

    companion object {
        fun newInstance() = DebugLogDspFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_debug_log_control_gimbal , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testResults.setText("")
        runTests()
    }

    fun runTests() {

        binding.testResults.setText("It may take a while to run the tests. \n\n")
        lifecycleScope.launchWhenStarted {

            viewModel.setAngleListenerTest()
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

            viewModel.getAdjustGimbalAngelDataTest()
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

            viewModel.getAngleRangeTest()
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

            viewModel.getAngleSpeedRangeTest()
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

            viewModel.setGimbalCalibrationTest()
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

            viewModel.stopGimbalCalibrationTest()
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

            viewModel.getVersionInfoTest()
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

            viewModel.setLeaserRadarListenerTest().observeOnce(viewLifecycleOwner, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })
            

            for (gimbalWorkMode in GimbalWorkMode.values()) {
                viewModel.setGimbalWorkModeTest(gimbalWorkMode)
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

                viewModel.getGimbalWorkModeTest()
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
            }

            for (pitch in 0..90 step 30) {
                for (roll in 0..90 step 30) {
                    for (yaw in 0..90 step 30) {
                        viewModel.setGimbalAngleTest(pitch.toFloat(), roll.toFloat(), yaw.toFloat())
                        viewModel.setSaveParamsTest()
                            .observeOnce(viewLifecycleOwner, Observer { msg ->
                                when (msg.status) {
                                    Status.SUCCESS -> {
                                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                    }
                                    Status.ERROR -> {
                                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                    }
                                    else -> {
                                        binding.testResults.append("No Response From Server")
                                    }
                                }
                            })
                    }
                }
            }

            for (gimbalAxisType in GimbalAxisType.values()) {
                viewModel.resetGimbalAngleTest(gimbalAxisType)
                    .observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                            }
                            else -> {
                                binding.testResults.append("No Response From Server")
                            }
                        }
                    })
            }

            for (pitch in 0..90 step 30) {
                for (yaw in 0..90 step 30) {
                    viewModel.setGimbalAngleSpeedTest(pitch, yaw)
                    viewModel.setSaveParamsTest()
                        .observeOnce(viewLifecycleOwner, Observer { msg ->
                            when (msg.status) {
                                Status.SUCCESS -> {
                                    binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                }
                                Status.ERROR -> {
                                    binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                }
                                else -> {
                                    binding.testResults.append("No Response From Server")
                                }
                            }
                        })

                    viewModel.getAngleSpeedRangeTest().observeOnce(viewLifecycleOwner, Observer { msg ->
                        when (msg.status) {
                            Status.SUCCESS -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                            }
                            Status.ERROR -> {
                                binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                            }
                            else -> {

                            }
                        }
                    })
                }
            }
            val x = 0.0F;val y = 0.0F
            for (pitch in 0..90 step 30) {
                for (roll in 0..90 step 30) {
                    for (yaw in 0..90 step 30) {
                        viewModel.adjustGimbalDirectionTest(x, y, pitch.toFloat(), roll.toFloat(), yaw.toFloat())
                            .observeOnce(viewLifecycleOwner, Observer { msg ->
                                when (msg.status) {
                                    Status.SUCCESS -> {
                                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                                    }
                                    Status.ERROR -> {
                                        binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                                    }
                                    else -> {
                                        binding.testResults.append("No Response From Server")
                                    }
                                }
                            })
                    }
                }
            }

            viewModel.setRollAdjustDataTest(0.0F)
            viewModel.setSaveParamsTest()
                .observeOnce(viewLifecycleOwner, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {
                            binding.testResults.append("No Response From Server")
                        }
                    }
                })
            viewModel.getRollAdjustDataTest().observeOnce(viewLifecycleOwner, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setPitchAdjustDataTest(0.0F)
            viewModel.setSaveParamsTest()
                .observeOnce(viewLifecycleOwner, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {
                            binding.testResults.append("No Response From Server")
                        }
                    }
                })

            viewModel.getPitchAdjustDataTest().observeOnce(viewLifecycleOwner, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.setYawAdjustDataTest(0.0F)
            viewModel.setSaveParamsTest()
                .observeOnce(viewLifecycleOwner, Observer { msg ->
                    when (msg.status) {
                        Status.SUCCESS -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.SUCCESS))
                        }
                        Status.ERROR -> {
                            binding.testResults.append(Utils.getColoredText(msg.message.toString(), Constants.FAILED))
                        }
                        else -> {
                            binding.testResults.append("No Response From Server")
                        }
                    }
                })

            viewModel.getYawAdjustDataTest().observeOnce(viewLifecycleOwner, Observer { msg ->
                when (msg.status) {
                    Status.SUCCESS -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.SUCCESS))
                    }
                    Status.ERROR -> {
                        binding.testResults.append(Utils.getColoredText(msg.message.toString(),Constants.FAILED))
                    }
                    else -> {

                    }
                }
            })

            viewModel.resetLeaserRadarListenerTest()

        }


    }


}