package com.android.autelsdk.battery.view

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.battery.BatteryViewModel
import com.android.autelsdk.battery.adapter.ManualIndividualItemAdapter
import com.android.autelsdk.battery.adapter.ManualIndividualViewHolder
import com.android.autelsdk.battery.data.HarnessResult
import com.android.autelsdk.databinding.AcManualIndividualFragmentBinding
import com.autel.sdk.battery.AutelBattery

class ManualIndividual_BatteryFragment : Fragment(), View.OnClickListener {
    lateinit var binding: AcManualIndividualFragmentBinding
    lateinit var viewModel: BatteryViewModel
    //val adapter = ManualIndividualItemAdapter()

    companion object {
        fun newInstance() = ManualIndividual_BatteryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AcManualIndividualFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(BatteryViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.individualRecyclerView.layoutManager = LinearLayoutManager(activity)
        initView()
    }

    private fun initView() {
        binding.setBtnBatteryRealTimeDataListener.setOnClickListener(this)
        binding.setBtnLowBatteryNotifyThresholdEdt.setOnClickListener(this)
        binding.setBtnCriticalBatteryNotifyThresholdEdt.setOnClickListener(this)
        binding.setBtnDischargeDayEdt.setOnClickListener(this)

        binding.viewBtnBatteryRealTimeDataListener.setOnClickListener(this)
        binding.viewBtnLowBatteryNotifyThresholdEdt.setOnClickListener(this)
        binding.viewBtnCriticalBatteryNotifyThresholdEdt.setOnClickListener(this)
        binding.viewBtnDischargeDayEdt.setOnClickListener(this)

        binding.buttonSetLowBatteryNotifyThresholdEdt.setOnClickListener(this)
        binding.buttonSetCriticalBatteryNotifyThresholdEdt.setOnClickListener(this)
        binding.buttonSetDischargeDayEdt.setOnClickListener(this)
    }

    private fun setSuccessResult(value: String, type: String) {
        when (type) {
            "BatteryRealTimeDataListener" -> {
                binding.textViewDisplayResultBatteryRealTimeDataListener.text = value
                binding.textViewDisplayResultBatteryRealTimeDataListener.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewDisplayResultBatteryRealTimeDataListener.visibility = View.VISIBLE
            }
            "LowBatteryNotifyThresholdEdt" -> {
                binding.textViewDisplayResultLowBatteryNotifyThresholdEdt.text = value
                binding.textViewDisplayResultLowBatteryNotifyThresholdEdt.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewDisplayResultLowBatteryNotifyThresholdEdt.visibility = View.VISIBLE
            }
            "CriticalBatteryNotifyThresholdEdt" -> {
                binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt.text = value
                binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt.visibility =
                    View.VISIBLE
            }
            "DischargeDayEdt" -> {
                binding.textViewDisplayDischargeDayEdt.text = value
                binding.textViewDisplayDischargeDayEdt.setTextColor(
                    Color.parseColor(
                        "#00ff00"
                    )
                )
                binding.textViewDisplayDischargeDayEdt.visibility = View.VISIBLE
            }
        }

    }

    private fun setFailureResult(value: String, type: String) {
        when (type) {
            "BatteryRealTimeDataListener" -> {
                binding.textViewDisplayResultBatteryRealTimeDataListener.text = value
                binding.textViewDisplayResultBatteryRealTimeDataListener.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewDisplayResultBatteryRealTimeDataListener.visibility = View.VISIBLE
            }
            "LowBatteryNotifyThresholdEdt" -> {
                binding.textViewDisplayResultLowBatteryNotifyThresholdEdt.text = value
                binding.textViewDisplayResultLowBatteryNotifyThresholdEdt.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewDisplayResultLowBatteryNotifyThresholdEdt.visibility = View.VISIBLE
            }
            "CriticalBatteryNotifyThresholdEdt" -> {
                binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt.text = value
                binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt.visibility =
                    View.VISIBLE
            }
            "DischargeDayEdt" -> {
                binding.textViewDisplayDischargeDayEdt.text = value
                binding.textViewDisplayDischargeDayEdt.setTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
                binding.textViewDisplayDischargeDayEdt.visibility = View.VISIBLE
            }
        }
    }

    private fun setLoadingResult(viewTextView: View) {
        val value = "Please wait..."
        (viewTextView as TextView).text = value
        (viewTextView as TextView).setTextColor(Color.parseColor("#000000"))
        (viewTextView as TextView).visibility = View.VISIBLE
    }

    private fun hideAllView() {
        binding.textViewDisplayResultBatteryRealTimeDataListener.visibility = View.GONE
        binding.textViewDisplayResultLowBatteryNotifyThresholdEdt.visibility = View.GONE
        binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt.visibility = View.GONE
        binding.textViewDisplayDischargeDayEdt.visibility = View.GONE
        binding.inputLayoutLowBatteryNotifyThresholdEdt.visibility = View.GONE
        binding.inputLayoutCriticalBatteryNotifyThresholdEdt.visibility = View.GONE
        binding.inputLayoutDischargeDayEdt.visibility = View.GONE
    }

    private fun displayResult(
        result: MutableLiveData<HarnessResult?>?, type: String
    ) {
        result?.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.status) {
                    setSuccessResult(it.value, type)
                } else {
                    setFailureResult(it.value, type)
                }
            } else {
                setFailureResult("error in loading data", type)
            }
        }
    }

    override fun onClick(p0: View?) {
        hideAllView()
        if (p0 != null) {
            when (p0.id) {
                R.id.setBtnBatteryRealTimeDataListener -> {
                    setLoadingResult(binding.textViewDisplayResultBatteryRealTimeDataListener)
                    val result = viewModel?.setBatteryRealTimeDataListener()
                    displayResult(result, "BatteryRealTimeDataListener")
                }

                R.id.setBtnLowBatteryNotifyThresholdEdt -> {
                    if (binding.inputLayoutLowBatteryNotifyThresholdEdt.visibility != View.VISIBLE) {
                        binding.editTextLowBatteryNotifyThresholdEdt.visibility = View.VISIBLE
                        binding.inputLayoutLowBatteryNotifyThresholdEdt.visibility = View.VISIBLE
                    } else {
                        binding.editTextLowBatteryNotifyThresholdEdt.visibility = View.GONE
                        binding.inputLayoutLowBatteryNotifyThresholdEdt.visibility = View.GONE
                    }
                }

                R.id.setBtnCriticalBatteryNotifyThresholdEdt -> {
                    if (binding.inputLayoutCriticalBatteryNotifyThresholdEdt.visibility != View.VISIBLE) {
                        binding.editTextCriticalBatteryNotifyThresholdEdt.visibility = View.VISIBLE
                        binding.inputLayoutCriticalBatteryNotifyThresholdEdt.visibility =
                            View.VISIBLE
                    } else {
                        binding.editTextCriticalBatteryNotifyThresholdEdt.visibility = View.GONE
                        binding.inputLayoutCriticalBatteryNotifyThresholdEdt.visibility =
                            View.GONE
                    }
                }

                R.id.setBtnDischargeDayEdt -> {
                    if (binding.inputLayoutDischargeDayEdt.visibility != View.VISIBLE) {
                        binding.editTextDischargeDayEdt.visibility = View.VISIBLE
                        binding.inputLayoutDischargeDayEdt.visibility = View.VISIBLE
                    } else {
                        binding.editTextDischargeDayEdt.visibility = View.GONE
                        binding.inputLayoutDischargeDayEdt.visibility = View.GONE
                    }
                }

                R.id.viewBtnBatteryRealTimeDataListener -> {
                    setLoadingResult(binding.textViewDisplayResultBatteryRealTimeDataListener)
                    val result = viewModel?.resetBatteryRealTimeDataListener()
                    displayResult(result, "BatteryRealTimeDataListener")
                }
                R.id.viewBtnLowBatteryNotifyThresholdEdt -> {
                    setLoadingResult(binding.textViewDisplayResultLowBatteryNotifyThresholdEdt)
                    displayResult(
                        viewModel?.getLowBatteryNotifyThresholdEdt(),
                        "LowBatteryNotifyThresholdEdt"
                    )
                }
                R.id.viewBtnCriticalBatteryNotifyThresholdEdt -> {
                    setLoadingResult(binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt)
                    displayResult(
                        viewModel?.getCriticalBatteryNotifyThresholdEdt(),
                        "CriticalBatteryNotifyThresholdEdt"
                    )
                }
                R.id.viewBtnDischargeDayEdt -> {
                    setLoadingResult(binding.textViewDisplayDischargeDayEdt)
                    displayResult(viewModel?.getDischargeDayEdt(), "DischargeDayEdt")
                }

                R.id.buttonSetLowBatteryNotifyThresholdEdt -> {
                    setLoadingResult(binding.textViewDisplayResultLowBatteryNotifyThresholdEdt)
                    displayResult(
                        viewModel?.setLowBatteryNotifyThresholdEdt(binding.editTextLowBatteryNotifyThresholdEdt.text.toString()),
                        "LowBatteryNotifyThresholdEdt"
                    )
                }

                R.id.buttonSetCriticalBatteryNotifyThresholdEdt -> {
                    setLoadingResult(binding.textViewDisplayResultCriticalBatteryNotifyThresholdEdt)
                    displayResult(
                        viewModel?.setCriticalBatteryNotifyThresholdEdt(binding.editTextCriticalBatteryNotifyThresholdEdt.text.toString()),
                        "CriticalBatteryNotifyThresholdEdt"
                    )
                }

                R.id.buttonSetDischargeDayEdt -> {
                    setLoadingResult(binding.textViewDisplayDischargeDayEdt)
                    displayResult(
                        viewModel?.setDischargeDayEdt(binding.editTextDischargeDayEdt.text.toString()),
                        "DischargeDayEdt"
                    )
                }
            }
        }
    }

}