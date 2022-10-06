package com.android.autelsdk.battery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.AutoTestResultAdapter
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.FragmentAutoTestDebugLogBinding
import com.android.autelsdk.databinding.FragmentDebugLogControlGimbalBinding
import com.android.autelsdk.util.Constants
import com.android.autelsdk.util.Utils
import com.android.autelsdk.util.Utils.observeOnce

class AutoTest_BatteryFragment : Fragment() {
    private lateinit var binding: FragmentAutoTestDebugLogBinding
    private val viewModel: BatteryViewModel by activityViewModels()

    val adapter = AutoTestResultAdapter()

    companion object {
        fun newInstance() = AutoTest_BatteryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_auto_test_debug_log,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testResults.text = ""
        // adapter.setListOfResult(HarnessResult("It may take a while to run the tests.", true))
        binding.resultRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.resultRecyclerView.adapter = adapter
        runTests()
    }

    fun startProgress(isToStart: Boolean) {
        if (isToStart) {
            binding.testResults.text = "It may take a while to run the tests."
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.testResults.text = "Test completed..."
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun runTests() {
        startProgress(true)
        lifecycleScope.launchWhenStarted {
            viewModel?.getDischargeCount()?.observeOnce(this@AutoTest_BatteryFragment) {
                if (it != null) {
                    adapter.setListOfResult(it as HarnessResult)
                } else {
                    adapter.setListOfResult(
                        HarnessResult(
                            "getDischargeCount() Error in result",
                            false
                        )
                    )
                }

                if (adapter.itemCount > 15)
                    startProgress(false)
            }

            viewModel?.getVersion()?.observeOnce(this@AutoTest_BatteryFragment) {
                if (it != null) {
                    adapter.setListOfResult(it as HarnessResult)
                } else {
                    adapter.setListOfResult(HarnessResult("getVersion() Error in result", false))
                }

                if (adapter.itemCount > 15)
                    startProgress(false)
            }

            viewModel?.getSerialNumber()?.observeOnce(this@AutoTest_BatteryFragment) {
                if (it != null) {
                    adapter.setListOfResult(it as HarnessResult)
                } else {
                    adapter.setListOfResult(
                        HarnessResult(
                            "getSerialNumber() Error in result",
                            false
                        )
                    )
                }

                if (adapter.itemCount > 15)
                    startProgress(false)
            }

            viewModel?.getFullChargeCapacity()?.observeOnce(this@AutoTest_BatteryFragment) {
                if (it != null) {
                    adapter.setListOfResult(it as HarnessResult)
                } else {
                    adapter.setListOfResult(
                        HarnessResult(
                            "getFullChargeCapacity() Error in result",
                            false
                        )
                    )
                }

                if (adapter.itemCount > 15)
                    startProgress(false)
            }

            viewModel?.getCellVoltageRange()?.observeOnce(this@AutoTest_BatteryFragment) {
                if (it != null) {
                    adapter.setListOfResult(it as HarnessResult)
                } else {
                    adapter.setListOfResult(
                        HarnessResult(
                            "getCellVoltageRange() Error in result",
                            false
                        )
                    )
                }

                if (adapter.itemCount > 15)
                    startProgress(false)
            }

            viewModel?.setBatteryRealTimeDataListener()
                ?.observeOnce(this@AutoTest_BatteryFragment) {
                    if (it != null) {
                        adapter.setListOfResult(it as HarnessResult)
                    } else {
                        adapter.setListOfResult(
                            HarnessResult(
                                "setBatteryRealTimeDataListener() Error in result",
                                false
                            )
                        )
                    }

                    if (adapter.itemCount > 15)
                        startProgress(false)
                }

            for (i in 0..3) {
                var value: String = "" + (i + 12)

                viewModel?.setLowBatteryNotifyThresholdEdt(value)
                    ?.observeOnce(this@AutoTest_BatteryFragment) {
                        if (it != null) {
                            adapter.setListOfResult(it as HarnessResult)
                        } else {
                            adapter.setListOfResult(
                                HarnessResult(
                                    "setLowBatteryNotifyThresholdEdt() Error in result",
                                    false
                                )
                            )
                        }

                        if (adapter.itemCount > 15)
                            startProgress(false)
                    }

                viewModel?.setCriticalBatteryNotifyThresholdEdt(value)
                    ?.observeOnce(this@AutoTest_BatteryFragment) {
                        if (it != null) {
                            adapter.setListOfResult(it as HarnessResult)
                        } else {
                            adapter.setListOfResult(
                                HarnessResult(
                                    "setCriticalBatteryNotifyThresholdEdt() Error in result",
                                    false
                                )
                            )
                        }

                        if (adapter.itemCount > 15)
                            startProgress(false)
                    }

                viewModel?.setDischargeDayEdt(value)
                    ?.observeOnce(this@AutoTest_BatteryFragment) {
                        if (it != null) {
                            adapter.setListOfResult(it as HarnessResult)
                        } else {
                            adapter.setListOfResult(
                                HarnessResult(
                                    "setDischargeDayEdt() Error in result",
                                    false
                                )
                            )
                        }

                        if (adapter.itemCount > 15)
                            startProgress(false)
                    }
            }

            viewModel?.resetBatteryRealTimeDataListener()
                ?.observeOnce(this@AutoTest_BatteryFragment) {
                    if (it != null) {
                        adapter.setListOfResult(it as HarnessResult)
                    } else {
                        adapter.setListOfResult(
                            HarnessResult(
                                "resetBatteryRealTimeDataListener() Error in result",
                                false
                            )
                        )
                    }

                    if (adapter.itemCount > 15)
                        startProgress(false)
                }

            viewModel?.getLowBatteryNotifyThresholdEdt()
                ?.observeOnce(this@AutoTest_BatteryFragment) {
                    if (it != null) {
                        adapter.setListOfResult(it as HarnessResult)
                    } else {
                        adapter.setListOfResult(
                            HarnessResult(
                                "getLowBatteryNotifyThresholdEdt() Error in result",
                                false
                            )
                        )
                    }

                    if (adapter.itemCount > 15)
                        startProgress(false)

                }

            viewModel?.getCriticalBatteryNotifyThresholdEdt()
                ?.observeOnce(this@AutoTest_BatteryFragment) {
                    if (it != null) {
                        adapter.setListOfResult(it as HarnessResult)
                    } else {
                        adapter.setListOfResult(
                            HarnessResult(
                                "getCriticalBatteryNotifyThresholdEdt() Error in result",
                                false
                            )
                        )
                    }

                    if (adapter.itemCount > 15)
                        startProgress(false)

                }

            viewModel?.getDischargeDayEdt()
                ?.observeOnce(this@AutoTest_BatteryFragment) {
                    if (it != null) {
                        adapter.setListOfResult(it as HarnessResult)
                    } else {
                        adapter.setListOfResult(
                            HarnessResult(
                                "getDischargeDayEdt() Error in result",
                                false
                            )
                        )
                    }

                    if (adapter.itemCount > 15)
                        startProgress(false)
                }

        }
    }
}