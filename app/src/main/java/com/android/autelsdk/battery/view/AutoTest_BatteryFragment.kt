package com.android.autelsdk.battery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.FragmentDebugLogControlGimbalBinding
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce

class AutoTest_BatteryFragment : Fragment() {
    private lateinit var binding: FragmentDebugLogControlGimbalBinding
    private val viewModel: BatteryViewModel by activityViewModels()

    companion object {
        fun newInstance() = AutoTest_BatteryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_debug_log_control_gimbal,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testResults.setText("")
        runTests()
    }

    private fun runTests() {
        binding.testResults.setText("It may take a while to run the tests. \n\n")
        lifecycleScope.launchWhenStarted {
            viewModel?.getDischargeCount()?.observeOnce(this@AutoTest_BatteryFragment) {

                if (it != null) {
                    if (it.status) {
                        binding.testResults.append(
                            Utils.getColoredText(
                                it.value,
                                Constants.SUCCESS
                            )
                        )
                    } else {
                        binding.testResults.append(Utils.getColoredText(it.value, Constants.FAILED))
                    }
                } else {
                    binding.testResults.append(
                        Utils.getColoredText(
                            "Error in result",
                            Constants.FAILED
                        )
                    )
                }
            }

            viewModel?.getVersion()?.observeOnce(this@AutoTest_BatteryFragment) {

                if (it != null) {
                    if (it.status) {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                it.value,
                                Constants.SUCCESS
                            )
                        )
                    } else {
                        binding.testResults.append( "\n\n"+Utils.getColoredText(it.value, Constants.FAILED))
                    }
                } else {
                    binding.testResults.append( "\n\n"+
                        Utils.getColoredText(
                            "Error in result",
                            Constants.FAILED
                        )
                    )
                }
            }

            viewModel?.getSerialNumber()?.observeOnce(this@AutoTest_BatteryFragment) {

                if (it != null) {
                    if (it.status) {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                it.value,
                                Constants.SUCCESS
                            )
                        )
                    } else {
                        binding.testResults.append( "\n\n"+Utils.getColoredText(it.value, Constants.FAILED))
                    }
                } else {
                    binding.testResults.append( "\n\n"+
                        Utils.getColoredText(
                            "Error in result",
                            Constants.FAILED
                        )
                    )
                }
            }

            viewModel?.getFullChargeCapacity()?.observeOnce(this@AutoTest_BatteryFragment) {

                if (it != null) {
                    if (it.status) {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                it.value,
                                Constants.SUCCESS
                            )
                        )
                    } else {
                        binding.testResults.append( "\n\n"+Utils.getColoredText(it.value, Constants.FAILED))
                    }
                } else {
                    binding.testResults.append( "\n\n"+
                        Utils.getColoredText(
                            "Error in result",
                            Constants.FAILED
                        )
                    )
                }
            }

            viewModel?.getCellVoltageRange()?.observeOnce(this@AutoTest_BatteryFragment) {

                if (it != null) {
                    if (it.status) {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                it.value,
                                Constants.SUCCESS
                            )
                        )
                    } else {
                        binding.testResults.append( "\n\n"+Utils.getColoredText(it.value, Constants.FAILED))
                    }
                } else {
                    binding.testResults.append( "\n\n"+
                        Utils.getColoredText(
                            "Error in result",
                            Constants.FAILED
                        )
                    )
                }
            }

            viewModel?.setBatteryRealTimeDataListener()
                ?.observeOnce(this@AutoTest_BatteryFragment) {

                    if (it != null) {
                        if (it.status) {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.SUCCESS
                                )
                            )
                        } else {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.FAILED
                                )
                            )
                        }
                    } else {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                "Error in result",
                                Constants.FAILED
                            )
                        )
                    }
                }

            for (i in 0..3) {
                var value: String = "" + (i + 12)

                viewModel?.setLowBatteryNotifyThresholdEdt(value)
                    ?.observeOnce(this@AutoTest_BatteryFragment) {

                        if (it != null) {
                            if (it.status) {
                                binding.testResults.append( "\n\n"+
                                    Utils.getColoredText(
                                        it.value,
                                        Constants.SUCCESS
                                    )
                                )
                            } else {
                                binding.testResults.append( "\n\n"+
                                    Utils.getColoredText(
                                        it.value,
                                        Constants.FAILED
                                    )
                                )
                            }
                        } else {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    "Error in result",
                                    Constants.FAILED
                                )
                            )
                        }
                    }

                viewModel?.setCriticalBatteryNotifyThresholdEdt(value)
                    ?.observeOnce(this@AutoTest_BatteryFragment) {

                        if (it != null) {
                            if (it.status) {
                                binding.testResults.append( "\n\n"+
                                    Utils.getColoredText(
                                        it.value,
                                        Constants.SUCCESS
                                    )
                                )
                            } else {
                                binding.testResults.append( "\n\n"+
                                    Utils.getColoredText(
                                        it.value,
                                        Constants.FAILED
                                    )
                                )
                            }
                        } else {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    "Error in result",
                                    Constants.FAILED
                                )
                            )
                        }
                    }

                viewModel?.setDischargeDayEdt(value)
                    ?.observeOnce(this@AutoTest_BatteryFragment) {

                        if (it != null) {
                            if (it.status) {
                                binding.testResults.append( "\n\n"+
                                    Utils.getColoredText(
                                        it.value,
                                        Constants.SUCCESS
                                    )
                                )
                            } else {
                                binding.testResults.append( "\n\n"+
                                    Utils.getColoredText(
                                        it.value,
                                        Constants.FAILED
                                    )
                                )
                            }
                        } else {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    "Error in result",
                                    Constants.FAILED
                                )
                            )
                        }
                    }
            }

            viewModel?.resetBatteryRealTimeDataListener()
                ?.observeOnce(this@AutoTest_BatteryFragment) {

                    if (it != null) {
                        if (it.status) {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.SUCCESS
                                )
                            )
                        } else {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.FAILED
                                )
                            )
                        }
                    } else {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                "Error in result",
                                Constants.FAILED
                            )
                        )
                    }
                }

            viewModel?.getLowBatteryNotifyThresholdEdt()
                ?.observeOnce(this@AutoTest_BatteryFragment) {

                    if (it != null) {
                        if (it.status) {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.SUCCESS
                                )
                            )
                        } else {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.FAILED
                                )
                            )
                        }
                    } else {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                "Error in result",
                                Constants.FAILED
                            )
                        )
                    }
                }

            viewModel?.getCriticalBatteryNotifyThresholdEdt()
                ?.observeOnce(this@AutoTest_BatteryFragment) {

                    if (it != null) {
                        if (it.status) {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.SUCCESS
                                )
                            )
                        } else {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.FAILED
                                )
                            )
                        }
                    } else {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                "Error in result",
                                Constants.FAILED
                            )
                        )
                    }
                }

            viewModel?.getDischargeDayEdt()
                ?.observeOnce(this@AutoTest_BatteryFragment) {

                    if (it != null) {
                        if (it.status) {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.SUCCESS
                                )
                            )
                        } else {
                            binding.testResults.append( "\n\n"+
                                Utils.getColoredText(
                                    it.value,
                                    Constants.FAILED
                                )
                            )
                        }
                    } else {
                        binding.testResults.append( "\n\n"+
                            Utils.getColoredText(
                                "Error in result",
                                Constants.FAILED
                            )
                        )
                    }
                }

        }
    }
}